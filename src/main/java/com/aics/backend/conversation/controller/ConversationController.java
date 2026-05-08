package com.aics.backend.conversation.controller;

import com.aics.backend.conversation.dto.ChatRequest;
import com.aics.backend.conversation.dto.ChatResponse;
import com.aics.backend.conversation.dto.ConversationDetailResponse;
import com.aics.backend.conversation.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Conversation API 컨트롤러입니다.
 * 채팅 시작/계속 및 상담방 상세 조회를 제공합니다.
 */
@RestController
@RequestMapping("/api/tenants/{tenantId}/conversations")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @PostMapping("/chat")
    /**
     * 채팅 요청을 처리합니다.
     * 사용자 메시지 저장 및 AI 응답 생성을 담당합니다.
     */
    public ResponseEntity<ChatResponse> chat(
            @PathVariable Long tenantId,
            @RequestBody ChatRequest request
    ) {
        ChatResponse response = conversationService.chat(tenantId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{conversationId}")
    /**
     * 특정 상담방의 메시지 히스토리를 조회합니다.
     */
    public ResponseEntity<ConversationDetailResponse> getConversationDetail(
            @PathVariable Long tenantId,
            @PathVariable Long conversationId
    ) {
        ConversationDetailResponse response = conversationService.getConversationDetail(tenantId, conversationId);
        return ResponseEntity.ok(response);
    }
}
