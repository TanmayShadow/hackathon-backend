package com.example.codeforbharat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://127.0.0.1:8000")
                .defaultHeader("X-API-Key", "Qw8nQh1k6n2vKjJ4w9lZp2t8Xy6bQz7rTg5vLm1pQw8nQh1k6n2v")
                .defaultHeader("X-Client-Id", "1")
                .build();
    }
}