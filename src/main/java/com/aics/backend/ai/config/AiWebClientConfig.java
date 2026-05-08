package com.aics.backend.ai.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(AiFastApiProperties.class)
public class AiWebClientConfig {

    @Bean
    public WebClient aiFastApiWebClient(
            WebClient.Builder builder,
            AiFastApiProperties properties
    ) {
        return builder
                .baseUrl(properties.baseUrl())
                .defaultHeader("X-Internal-Api-Key", properties.internalApiKey())
                .build();
    }
}