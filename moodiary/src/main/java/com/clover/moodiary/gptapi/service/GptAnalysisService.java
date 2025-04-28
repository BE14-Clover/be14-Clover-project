package com.clover.moodiary.gptapi.service;

import com.clover.moodiary.gptapi.external.GptApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptAnalysisService {

    private final GptApiClient gptApiClient;

    public String analyzeDiary(String diaryContent) {
        String prompt = createPrompt(diaryContent);
        return gptApiClient.sendPrompt(prompt);
    }

    private String createPrompt(String diaryContent) {
        return "아래 일기를 읽고 감정 분석을 해주세요.\n\n" +
                "일기 내용:\n" + diaryContent + "\n\n" +
                "결과는 다음 JSON 형태로 반환해 주세요: {\n" +
                "\"summary\": \"한 줄 요약\",\n" +
                "\"mainEmotion\": \"주된 감정\",\n" +
                "\"subEmotions\": [\"감정1\", \"감정2\"],\n" +
                "\"scoreSummary\": {\n" +
                "  \"positive\": 점수,\n" +
                "  \"neutral\": 점수,\n" +
                "  \"negative\": 점수,\n" +
                "  \"total\": 총점\n" +
                "}\n" +
                "}";
    }
}