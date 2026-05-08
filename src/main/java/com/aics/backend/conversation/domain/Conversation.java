package com.aics.backend.conversation.domain;

import com.aics.backend.global.type.ConversationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Conversation(Long tenantId) {
        this.tenantId = tenantId;
        this.status = ConversationStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void requestHandoff() {
        this.status = ConversationStatus.WAITING_AGENT;
        this.updatedAt = LocalDateTime.now();
    }

    public void close() {
        this.status = ConversationStatus.CLOSED;
        this.updatedAt = LocalDateTime.now();
    }
}
