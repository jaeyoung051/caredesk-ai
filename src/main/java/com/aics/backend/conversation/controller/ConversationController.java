package com.aics.backend.conversation.controller;

import com.aics.backend.conversation.dto.ChatRequest;
import com.aics.backend.conversation.dto.ChatResponse;
import com.aics.backend.conversation.dto.ConversationDetailResponse;
import com.aics.backend.conversation.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants/{tenantId}/conversations")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
            @PathVariable Long tenantId,
            @RequestBody ChatRequest request
    ) {
        ChatResponse response = conversationService.chat(tenantId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationDetailResponse> getConversationDetail(
            @PathVariable Long tenantId,
            @PathVariable Long conversationId
    ) {
        ConversationDetailResponse response = conversationService.getConversationDetail(tenantId, conversationId);
        return ResponseEntity.ok(response);
    }
}
