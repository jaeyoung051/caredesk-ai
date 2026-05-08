package com.aics.backend.knowledge.domain;

import com.aics.backend.global.type.IndexStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * RAG 인덱싱 대상 문서를 나타냅니다.
 * 저장된 문서는 이후 AI 검색/응답에 활용될 수 있습니다.
 */
@Entity
@Table(name = "knowledge_documents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KnowledgeDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tenantId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IndexStatus indexStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public KnowledgeDocument(Long tenantId, String title, String content) {
        this.tenantId = tenantId;
        this.title = title;
        this.content = content;
        this.indexStatus = IndexStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markIndexing() {
        this.indexStatus = IndexStatus.INDEXING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markIndexed() {
        this.indexStatus = IndexStatus.INDEXED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markFailed() {
        this.indexStatus = IndexStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }
}
