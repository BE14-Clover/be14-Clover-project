package com.clover.moodiary.api.gpt.service;

import com.clover.moodiary.api.gpt.command.dto.GptResponseDto;
import com.clover.moodiary.api.gpt.config.OpenAiKeysConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class GptResponseParser {

    // private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, String> extractFieldsFromResponse(String apiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // 1. 전체 응답 파싱
            JsonNode root = objectMapper.readTree(apiResponse);
            JsonNode contentNode = root.path("choices").get(0).path("message").path("content");

            if (contentNode.isMissingNode() || contentNode.isNull()) {
                throw new RuntimeException("GPT 응답에서 content를 찾을 수 없습니다.");
            }

            // 2. content: 줄바꿈/탭/여러 공백 제거 후 깨끗한 JSON String으로 정리
            String rawJson = contentNode.asText();
            String cleanedJson = rawJson
                    .replaceAll("\\\\n", "") // 이스케이프된 줄바꿈 제거
                    .replaceAll("\\\\r", "") // 이스케이프된 캐리지리턴 제거
                    .replaceAll("\\s{2,}", " ") // 여러 개 공백을 하나로 줄이기
                    .trim();

            // 3. 다시 Map으로 변환
            return objectMapper.readValue(cleanedJson, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GptAnalysisException("GPT 응답 파싱 실패", e);
        }
    }

    public static GptResponseDto toDto(Map<String, String> parsedMap, OpenAiKeysConfig keys) {
        GptResponseDto dto = new GptResponseDto();

        dto.setPositiveScore(parseIntSafe(parsedMap.getOrDefault(keys.getPositiveScore(), "0")));
        dto.setNeutralScore(parseIntSafe(parsedMap.getOrDefault(keys.getNeutralScore(), "0")));
        dto.setNegativeScore(parseIntSafe(parsedMap.getOrDefault(keys.getNegativeScore(), "0")));
        dto.setTotalScore(parseIntSafe(parsedMap.getOrDefault(keys.getTotalScore(), "0")));

        dto.setEmotion1(defaultIfEmpty(parsedMap.get(keys.getEmotion1())));
        dto.setEmotion2(defaultIfEmpty(parsedMap.get(keys.getEmotion2())));
        dto.setEmotion3(defaultIfEmpty(parsedMap.get(keys.getEmotion3())));

        String title = parsedMap.get(keys.getDiaryTitle());
        if (title == null || title.trim().isEmpty() || title.equals("\\")) {
            title = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        dto.setDiaryTitle(title);

        return dto;
    }

    private static int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private static String defaultIfEmpty(String value) {
        return (value == null || value.trim().isEmpty() || value.equals("\\")) ? "없음" : value;
    }
}