package com.clover.moodiary.gptapi.external;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Component
public class GptApiClient {

    public String callGptApi(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.openai.com/v1/your-gpt-endpoint"; // 실제 엔드포인트로 바꿔야 함

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("your-openai-api-key");

        String requestBody = "{ \"prompt\": \"" + prompt + "\", \"max_tokens\": 100 }";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody();
    }
}