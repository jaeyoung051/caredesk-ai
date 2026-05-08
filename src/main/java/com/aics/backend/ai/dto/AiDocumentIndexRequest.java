package com.aics.backend.ai.dto;

public record AiDocumentIndexRequest(
        Long companyId,
        Long documentId,
        String filePath,
        String title
) {
}