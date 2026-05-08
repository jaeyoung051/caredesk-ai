package com.aics.backend.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ai.fastapi")
public record AiFastApiProperties(
        String baseUrl,
        String internalApiKey,
        int timeoutSeconds
) {
}