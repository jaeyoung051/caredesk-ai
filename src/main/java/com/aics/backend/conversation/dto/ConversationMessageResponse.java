package com.aics.backend.conversation.dto;

import com.aics.backend.global.type.MessageRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationMessageResponse {
    private Long messageId;
    private MessageRole role;
    private String content;
    private Double confidence;
    private Boolean grounded;
    private Boolean needHandoff;
    private LocalDateTime createdAt;
}
