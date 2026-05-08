package com.aics.backend.ai.client;

import com.aics.backend.ai.dto.AiRagAskRequest;
import com.aics.backend.ai.dto.AiRagAskResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 현재 FastAPI RAG/Agent 서버가 준비되지 않아 사용하는 Mock AI 클라이언트입니다.
 * 실제 서버가 준비되면 AiFastApiClient 또는 AiService로 교체할 예정입니다.
 */
@Component
public class MockAiClient {

    /**
     * Mock RAG 질문 응답을 생성합니다.
     * @param request AI RAG 요청 정보
     * @return 테스트용 답변과 메타데이터
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
