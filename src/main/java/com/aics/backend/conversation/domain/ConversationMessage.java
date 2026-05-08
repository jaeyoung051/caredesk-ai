package com.aics.backend.conversation.domain;

import com.aics.backend.global.type.MessageRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private Long conversationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageRole role;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Double confidence;

    private Boolean grounded;

    private Boolean needHandoff;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ConversationMessage(
            Long tenantId,
            Long conversationId,
            MessageRole role,
            String content,
            Double confidence,
            Boolean grounded,
            Boolean needHandoff
    ) {
        this.tenantId = tenantId;
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
        this.confidence = confidence;
        this.grounded = grounded;
        this.needHandoff = needHandoff;
        this.createdAt = LocalDateTime.now();
    }
}
