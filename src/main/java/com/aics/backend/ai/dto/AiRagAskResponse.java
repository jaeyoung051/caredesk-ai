package com.aics.backend.ai.dto;

import java.util.List;

public record AiRagAskResponse(
        String answer,
        String intentName,
        String handlingType,
        Double confidence,
        List<AiSourceChunk> sources,
        String llmProvider,
        String llmModel
) {
}