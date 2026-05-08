package com.aics.backend.ai.client;

import com.aics.backend.ai.config.AiFastApiProperties;
import com.aics.backend.ai.dto.AiDocumentIndexRequest;
import com.aics.backend.ai.dto.AiDocumentIndexResponse;
import com.aics.backend.ai.dto.AiRagAskRequest;
import com.aics.backend.ai.dto.AiRagAskResponse;
import java.time.Duration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AiFastApiClient {

    private final WebClient aiFastApiWebClient;
    private final AiFastApiProperties properties;

    public AiFastApiClient(
            WebClient aiFastApiWebClient,
            AiFastApiProperties properties
    ) {
        this.aiFastApiWebClient = aiFastApiWebClient;
        this.properties = properties;
    }

    public AiRagAskResponse askRag(AiRagAskRequest request) {
        return aiFastApiWebClient.post()
                .uri("/api/ai/rag/ask")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AiRagAskResponse.class)
                .block(Duration.ofSeconds(properties.timeoutSeconds()));
    }

    public AiDocumentIndexResponse indexDocument(AiDocumentIndexRequest request) {
        return aiFastApiWebClient.post()
                .uri("/api/ai/documents/index")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AiDocumentIndexResponse.class)
                .block(Duration.ofSeconds(properties.timeoutSeconds()));
    }
}