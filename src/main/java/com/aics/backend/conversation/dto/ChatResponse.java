package com.aics.backend.conversation.dto;

import com.aics.backend.ai.dto.AiSourceChunk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private Long conversationId;
    private String answer;
    private String intent;
    private Double confidence;
    private Boolean grounded;
    private Boolean needHandoff;
    private List<AiSourceChunk> sources;
}
