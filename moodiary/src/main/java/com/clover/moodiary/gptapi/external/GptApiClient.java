package com.clover.moodiary.gptapi.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GptApiClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader("Authorization", "Bearer YOUR_OPENAI_API_KEY")
            .build();

    public String sendPrompt(String prompt) {
        // 여기는 너네가 원하는 방식에 따라 GPT 호출
        return webClient.post()
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String buildRequestBody(String prompt) {
        return "{\n" +
                "  \"model\": \"gpt-4\",\n" +
                "  \"messages\": [\n" +
                "    { \"role\": \"system\", \"content\": \"당신은 감정 분석 전문가입니다.\" },\n" +
                "    { \"role\": \"user\", \"content\": \"" + prompt.replace("\"", "\\\"") + "\" }\n" +
                "  ]\n" +
                "}";
    }
}