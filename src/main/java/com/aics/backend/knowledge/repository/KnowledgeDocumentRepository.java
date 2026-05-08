package com.aics.backend.knowledge.repository;

import com.aics.backend.knowledge.domain.KnowledgeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgeDocumentRepository extends JpaRepository<KnowledgeDocument, Long> {
    List<KnowledgeDocument> findByTenantIdOrderByCreatedAtDesc(Long tenantId);
}
