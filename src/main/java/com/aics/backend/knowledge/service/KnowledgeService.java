package com.aics.backend.knowledge.service;

import com.aics.backend.knowledge.domain.KnowledgeDocument;
import com.aics.backend.knowledge.dto.KnowledgeCreateRequest;
import com.aics.backend.knowledge.dto.KnowledgeCreateResponse;
import com.aics.backend.knowledge.dto.KnowledgeResponse;
import com.aics.backend.knowledge.repository.KnowledgeDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 지식 문서 저장 및 조회를 담당하는 서비스입니다.
 *
 * 문서 저장 후 인덱싱 요청을 이어갈 수 있는 구조로 설계되어 있습니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class KnowledgeService {
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;

    /**
     * 새 지식 문서를 생성합니다.
     * 생성된 문서는 RAG 인덱싱 대상 문서로 취급됩니다.
     */
    public KnowledgeCreateResponse create(Long tenantId, KnowledgeCreateRequest request) {
        KnowledgeDocument document = new KnowledgeDocument(
                tenantId,
                request.getTitle(),
                request.getContent()
        );
        KnowledgeDocument saved = knowledgeDocumentRepository.save(document);

        return new KnowledgeCreateResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getIndexStatus()
        );
    }

    @Transactional(readOnly = true)
    /**
     * 테넌트의 지식 문서 목록을 조회합니다.
     */
    public List<KnowledgeResponse> findAll(Long tenantId) {
        return knowledgeDocumentRepository.findByTenantIdOrderByCreatedAtDesc(tenantId)
                .stream()
                .map(doc -> new KnowledgeResponse(
                        doc.getId(),
                        doc.getTitle(),
                        doc.getContent(),
                        doc.getIndexStatus(),
                        doc.getCreatedAt(),
                        doc.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }
}
