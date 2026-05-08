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

/**
 * Knowledge API 컨트롤러입니다.
 * 문서 생성 및 테넌트별 지식 문서 목록 조회를 제공합니다.
 */
@RestController
@RequestMapping("/api/tenants/{tenantId}/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    @PostMapping
    /**
     * 지식 문서를 생성합니다.
     * 생성 후 즉시 저장되며, 추후 인덱싱 요청을 연결할 수 있습니다.
     */
    public ResponseEntity<KnowledgeCreateResponse> createKnowledge(
            @PathVariable Long tenantId,
            @RequestBody KnowledgeCreateRequest request
    ) {
        KnowledgeCreateResponse response = knowledgeService.create(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    /**
     * 테넌트의 지식 문서 목록을 조회합니다.
     */
    public ResponseEntity<List<KnowledgeResponse>> getKnowledgeList(
            @PathVariable Long tenantId
    ) {
        List<KnowledgeResponse> responses = knowledgeService.findAll(tenantId);
        return ResponseEntity.ok(responses);
    }
}
