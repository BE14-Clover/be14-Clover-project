package com.clover.moodiary.gptapi.query.service;

import com.clover.moodiary.gptapi.external.GptApiClient;
import com.clover.moodiary.gptapi.query.dto.GptResultResponse;
import org.springframework.stereotype.Service;

@Service
public class GptQueryService {

    private final GptApiClient gptApiClient;

    public GptQueryService(GptApiClient gptApiClient) {
        this.gptApiClient = gptApiClient;
    }

    public GptResultResponse fetchGptResult(String prompt) {
        String apiResult = gptApiClient.callGptApi(prompt);
        // 여기서 JSON 파싱 필요 시 추가 (예시에서는 생략)
        return new GptResultResponse(apiResult);
    }
}