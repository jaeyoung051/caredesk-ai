package com.aics.backend.knowledge.dto;

import com.aics.backend.global.type.IndexStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeResponse {
    private Long knowledgeId;
    private String title;
    private String content;
    private IndexStatus indexStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
