package com.aics.backend.faq.domain;

import com.aics.backend.global.type.FaqDraftStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "faq_drafts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FaqDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FaqDraftStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public FaqDraft(Long tenantId, String question, String answer) {
        this.tenantId = tenantId;
        this.question = question;
        this.answer = answer;
        this.status = FaqDraftStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
}
