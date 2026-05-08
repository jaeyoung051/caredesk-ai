package com.aics.backend.knowledge.controller;

import com.aics.backend.knowledge.dto.KnowledgeCreateRequest;
import com.aics.backend.knowledge.dto.KnowledgeCreateResponse;
import com.aics.backend.knowledge.dto.KnowledgeResponse;
import com.aics.backend.knowledge.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants/{tenantId}/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    @PostMapping
    public ResponseEntity<KnowledgeCreateResponse> createKnowledge(
            @PathVariable Long tenantId,
            @RequestBody KnowledgeCreateRequest request
    ) {
        KnowledgeCreateResponse response = knowledgeService.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<KnowledgeResponse>> getKnowledgeList(
            @PathVariable Long tenantId
    ) {
        List<KnowledgeResponse> responses = knowledgeService.findAll(tenantId);
        return ResponseEntity.ok(responses);
    }
}
