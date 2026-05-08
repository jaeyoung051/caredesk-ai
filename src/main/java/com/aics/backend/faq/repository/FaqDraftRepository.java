package com.aics.backend.faq.repository;

import com.aics.backend.faq.domain.FaqDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqDraftRepository extends JpaRepository<FaqDraft, Long> {
}
