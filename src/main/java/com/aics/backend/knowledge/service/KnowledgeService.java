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

@Service
@RequiredArgsConstructor
@Transactional
public class KnowledgeService {
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;

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
