package com.aics.backend.ai.dto;

import java.util.List;

public record AiDocumentIndexResponse(
        Long documentId,
        String status,
        Integer chunkCount,
        List<String> sampleChunks
) {
}