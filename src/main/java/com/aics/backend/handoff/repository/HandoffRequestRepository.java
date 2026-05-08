package com.aics.backend.handoff.repository;

import com.aics.backend.handoff.domain.HandoffRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandoffRequestRepository extends JpaRepository<HandoffRequest, Long> {
}
