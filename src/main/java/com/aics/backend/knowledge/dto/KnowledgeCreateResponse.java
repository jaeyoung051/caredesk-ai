package com.aics.backend.knowledge.dto;

import com.aics.backend.global.type.IndexStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeCreateResponse {
    private Long knowledgeId;
    private String title;
    private IndexStatus indexStatus;
}
