package com.clover.moodiary.api.gpt.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.clover.moodiary.api.gpt.command.dto.GptRequestDto;

import reactor.core.publisher.Mono;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Component
public class GptApiClient {

        private final WebClient webClient;

        @Value("${openai.api.url}") // yml에서 URL 읽기
        private String gptApiUrl;

        @Value("${openai.api.key}") // yml에서 API Key 읽기
        private String apiKey;

        @Value("${openai.api.project-id}")
        private String projectId;

        public GptApiClient(WebClient.Builder webClientBuilder) {
                this.webClient = webClientBuilder.build();
        }

        public Mono<String> sendPrompt(GptRequestDto gptRequestDto) {
                return webClient.post()
                                .uri(gptApiUrl) // 요청할 때 직접 URL 지정
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                                .header("OpenAI-Project", projectId) // 🔥 프로젝트 ID 헤더 추가 꼭
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(gptRequestDto)
                                .retrieve()
                                .bodyToMono(String.class)
                                .timeout(Duration.ofSeconds(10))
                                .onErrorResume(e -> Mono.just("Error: " + e.getMessage()));
        }
}