package com.clover.moodiary.gptapi.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Component
public class GptApiClient {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}") // 환경파일에서 읽어오기!
    private String apiKey;

    public String callGptApi(String prompt) {
        // 요청 바디 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4o"); // 모델: gpt-4o
        requestBody.put("temperature", 0.7);

        requestBody.put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", prompt)));

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // 여기!!!

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                request,
                String.class);

        // 응답 파싱
        JSONObject responseBody = new JSONObject(response.getBody());
        String result = responseBody
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        return result.trim();
    }
}