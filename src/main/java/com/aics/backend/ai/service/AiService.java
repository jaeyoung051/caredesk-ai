package com.aics.backend.ai.service;

import com.aics.backend.ai.client.AiFastApiClient;
import com.aics.backend.ai.dto.AiDocumentIndexRequest;
import com.aics.backend.ai.dto.AiDocumentIndexResponse;
import com.aics.backend.ai.dto.AiRagAskRequest;
import com.aics.backend.ai.dto.AiRagAskResponse;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final AiFastApiClient aiFastApiClient;

    public AiService(AiFastApiClient aiFastApiClient) {
        this.aiFastApiClient = aiFastApiClient;
    }

    public AiRagAskResponse ask(
            Long companyId,
            Long conversationId,
            String question
    ) {
        AiRagAskRequest request = new AiRagAskRequest(
                companyId,
                conversationId,
                question,
                5
        );

        return aiFastApiClient.askRag(request);
    }

    public AiDocumentIndexResponse indexDocument(
            Long companyId,
            Long documentId,
            String filePath,
            String title
    ) {
        AiDocumentIndexRequest request = new AiDocumentIndexRequest(
                companyId,
                documentId,
                filePath,
                title
        );

        return aiFastApiClient.indexDocument(request);
    }
}