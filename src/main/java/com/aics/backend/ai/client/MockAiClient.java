package com.aics.backend.ai.client;

import com.aics.backend.ai.dto.AiRagAskRequest;
import com.aics.backend.ai.dto.AiRagAskResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Mock AI Client for testing without FastAPI server
 * This client returns predefined test responses and doesn't call the actual FastAPI server.
 * When the FastAPI server is ready, replace this with calls to AiFastApiClient.
 */
@Component
public class MockAiClient {

    /**
     * Mock RAG question answering
     * @param request AI RAG ask request containing question and other parameters
     * @return Mock response with test answer and metadata
     */
    public AiRagAskResponse ask(AiRagAskRequest request) {
        return new AiRagAskResponse(
                "현재 AI 서버 준비 전 테스트 응답입니다. 실제 RAG 응답은 추후 연결됩니다.",
                "FAQ_QUESTION",
                "AUTO",
                0.5,
                Collections.emptyList(),
                "MOCK",
                "MOCK"
        );
    }
}
