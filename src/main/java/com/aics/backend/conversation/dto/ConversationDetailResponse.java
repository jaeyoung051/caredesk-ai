package com.aics.backend.conversation.dto;

import com.aics.backend.global.type.ConversationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDetailResponse {
    private Long conversationId;
    private ConversationStatus status;
    private List<ConversationMessageResponse> messages;
}
