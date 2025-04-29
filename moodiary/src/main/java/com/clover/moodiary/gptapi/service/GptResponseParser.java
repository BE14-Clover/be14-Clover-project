package com.clover.moodiary.gptapi.service;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Slf4j
public class GptResponseParser {

    public static ParsedGptResult parseYamlResponse(String yamlText) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(yamlText);

            ParsedGptResult result = new ParsedGptResult();
            result.setPositive((Integer) ((Map<String, Object>) map.get("총 감정 점수 요약")).get("긍정 감정 점수"));
            result.setNeutral((Integer) ((Map<String, Object>) map.get("총 감정 점수 요약")).get("보통 감정 점수"));
            result.setNegative((Integer) ((Map<String, Object>) map.get("총 감정 점수 요약")).get("부정 감정 점수"));
            result.setTotal((Integer) ((Map<String, Object>) map.get("총 감정 점수 요약")).get("총합 감정 점수"));
            result.setSummary((String) map.get("한 줄 요약"));
            result.setEmotions((java.util.List<String>) map.get("감정 요약"));
            return result;

        } catch (Exception e) {
            log.error("GPT 응답 파싱 실패", e);
            throw new RuntimeException("GPT 응답 파싱 실패: " + e.getMessage());
        }
    }

    public static class ParsedGptResult {
        private int positive;
        private int neutral;
        private int negative;
        private int total;
        private String summary;
        private java.util.List<String> emotions;

        // Getter/Setter
        public int getPositive() {
            return positive;
        }

        public void setPositive(int positive) {
            this.positive = positive;
        }

        public int getNeutral() {
            return neutral;
        }

        public void setNeutral(int neutral) {
            this.neutral = neutral;
        }

        public int getNegative() {
            return negative;
        }

        public void setNegative(int negative) {
            this.negative = negative;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public java.util.List<String> getEmotions() {
            return emotions;
        }

        public void setEmotions(java.util.List<String> emotions) {
            this.emotions = emotions;
        }
    }
}