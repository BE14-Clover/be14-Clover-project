package com.clover.moodiary.gptapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import com.clover.moodiary.gptapi.command.dto.GptResponseDto;

public class GptResponseParser {

    public static Map<String, String> extractFieldsFromResponse(String apiResponse) {
        try {
            // 1. "choices"부터 "message"까지 수동으로 찾기
            int messageIndex = apiResponse.indexOf("\"message\"");
            int contentIndex = apiResponse.indexOf("\"content\"", messageIndex);

            int colonIndex = apiResponse.indexOf(":", contentIndex);
            int firstQuoteIndex = apiResponse.indexOf("\"", colonIndex + 1);
            int lastQuoteIndex = apiResponse.indexOf("\"", firstQuoteIndex + 1);

            String content = apiResponse.substring(firstQuoteIndex + 1, lastQuoteIndex);

            // 2. 줄 단위로 자르기
            String[] lines = content.split("\\\\n");

            // 3. 줄별로 파싱
            Map<String, String> result = new LinkedHashMap<>();

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                int separatorIndex = line.indexOf(":");
                if (separatorIndex == -1)
                    continue;

                String key = line.substring(0, separatorIndex).trim();
                String value = line.substring(separatorIndex + 1).trim();

                // ⭐ 따옴표(")로 감싸져 있으면 따옴표 제거
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                result.put(key, value);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GptResponseDto toDto(Map<String, String> parsedMap) {
        GptResponseDto dto = new GptResponseDto();

        dto.setPositiveScore(parseIntSafe(parsedMap.get("긍정 감정 점수")));
        dto.setNeutralScore(parseIntSafe(parsedMap.get("보통 감정 점수")));
        dto.setNegativeScore(parseIntSafe(parsedMap.get("부정 감정 점수")));
        dto.setTotalScore(parseIntSafe(parsedMap.get("총합 감정 점수")));

        dto.setEmotion1(parsedMap.getOrDefault("대표 감정 1", ""));
        dto.setEmotion2(parsedMap.getOrDefault("대표 감정 2", ""));
        dto.setEmotion3(parsedMap.getOrDefault("대표 감정 3", ""));

        // ⭐ 일기 제목 처리
        String title = parsedMap.get("일기 제목");
        if (title == null || title.trim().isEmpty() || title.equals("\\")) {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            title = now.format(formatter);
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
}