package com.aics.backend.ai.dto;

public record AiSourceChunk(
        Long documentId,
        String chunkId,
        String title,
        Double score
) {
}