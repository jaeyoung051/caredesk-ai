package com.aics.backend.handoff.domain;

import com.aics.backend.global.type.HandoffStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "handoff_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HandoffRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private Long conversationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HandoffStatus status;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public HandoffRequest(Long tenantId, Long conversationId) {
        this.tenantId = tenantId;
        this.conversationId = conversationId;
        this.status = HandoffStatus.REQUESTED;
        this.createdAt = LocalDateTime.now();
    }
}
