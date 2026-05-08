package com.aics.backend.conversation.service;

import com.aics.backend.ai.client.MockAiClient;
import com.aics.backend.ai.dto.AiRagAskRequest;
import com.aics.backend.ai.dto.AiRagAskResponse;
import com.aics.backend.conversation.domain.Conversation;
import com.aics.backend.conversation.domain.ConversationMessage;
import com.aics.backend.conversation.dto.ChatRequest;
import com.aics.backend.conversation.dto.ChatResponse;
import com.aics.backend.conversation.dto.ConversationDetailResponse;
import com.aics.backend.conversation.dto.ConversationMessageResponse;
import com.aics.backend.conversation.repository.ConversationMessageRepository;
import com.aics.backend.conversation.repository.ConversationRepository;
import com.aics.backend.global.type.MessageRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationMessageRepository conversationMessageRepository;
    private final MockAiClient mockAiClient;

    public ChatResponse chat(Long tenantId, ChatRequest request) {
        // Step 1: Get or create conversation
        Conversation conversation;
        if (request.getConversationId() == null) {
            conversation = new Conversation(tenantId);
            conversation = conversationRepository.save(conversation);
        } else {
            conversation = conversationRepository.findById(request.getConversationId())
                    .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + request.getConversationId()));
            
            // Validate tenant ID matches
            if (!conversation.getTenantId().equals(tenantId)) {
                throw new IllegalArgumentException("Tenant ID mismatch");
            }
        }

        Long conversationId = conversation.getId();

        // Step 2: Save user message
        ConversationMessage userMessage = new ConversationMessage(
                tenantId,
                conversationId,
                MessageRole.USER,
                request.getMessage(),
                null,
                null,
                null
        );
        conversationMessageRepository.save(userMessage);

        // Step 3: Call MockAiClient
        AiRagAskRequest aiRequest = new AiRagAskRequest(
                tenantId,
                conversationId,
                request.getMessage(),
                10
        );
        AiRagAskResponse aiResponse = mockAiClient.ask(aiRequest);

        // Step 4: Save AI response message
        boolean needHandoff = false; // Default to false for mock response
        ConversationMessage aiMessage = new ConversationMessage(
                tenantId,
                conversationId,
                MessageRole.ASSISTANT,
                aiResponse.answer(),
                aiResponse.confidence(),
                false, // grounded
                needHandoff
        );
        conversationMessageRepository.save(aiMessage);

        // Step 5: Handle handoff if needed
        if (needHandoff) {
            conversation.requestHandoff();
        }

        // Step 6: Build and return response
        return new ChatResponse(
                conversationId,
                aiResponse.answer(),
                aiResponse.intentName(),
                aiResponse.confidence(),
                false, // grounded
                needHandoff,
                aiResponse.sources()
        );
    }

    @Transactional(readOnly = true)
    public ConversationDetailResponse getConversationDetail(Long tenantId, Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + conversationId));

        // Validate tenant ID matches
        if (!conversation.getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Tenant ID mismatch");
        }

        List<ConversationMessage> messages = conversationMessageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
        List<ConversationMessageResponse> messageResponses = messages.stream()
                .map(msg -> new ConversationMessageResponse(
                        msg.getId(),
                        msg.getRole(),
                        msg.getContent(),
                        msg.getConfidence(),
                        msg.getGrounded(),
                        msg.getNeedHandoff(),
                        msg.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new ConversationDetailResponse(
                conversationId,
                conversation.getStatus(),
                messageResponses
        );
    }
}
