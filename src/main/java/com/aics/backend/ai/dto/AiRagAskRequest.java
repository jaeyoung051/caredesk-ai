package com.aics.backend.ai.dto;

public record AiRagAskRequest(
        Long companyId,
        Long conversationId,
        String question,
        Integer topK
) {
}