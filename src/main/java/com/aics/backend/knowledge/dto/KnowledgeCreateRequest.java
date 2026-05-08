package com.aics.backend.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeCreateRequest {
    private String title;
    private String content;
}
