package com.aics.backend.ai.controller;

import com.aics.backend.ai.dto.AiDocumentIndexResponse;
import com.aics.backend.ai.dto.AiRagAskResponse;
import com.aics.backend.ai.service.AiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiTestController {

    private final AiService aiService;

    public AiTestController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/test/ai/index")
    public AiDocumentIndexResponse testIndex() {
        return aiService.indexDocument(
                1L,
                10L,
                "./data/uploads/refund-policy.txt",
                "환불 정책"
        );
    }

    @GetMapping("/test/ai/ask")
    public AiRagAskResponse testAsk() {
        return aiService.ask(
                1L,
                100L,
                "환불 가능한가요?"
        );
    }
}