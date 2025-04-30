package com.clover.moodiary.api.gpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.clover.moodiary.api.gpt.command.dto.GptRequestDto;
import com.clover.moodiary.api.gpt.command.dto.GptResponseDto;
import com.clover.moodiary.api.gpt.config.OpenAiKeysConfig;
import com.clover.moodiary.api.gpt.external.GptApiClient;

import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptAnalysisService {

    private final GptApiClient gptApiClient;
    private final OpenAiKeysConfig keysConfig;

    public Mono<GptResponseDto> analyzeDiary(GptRequestDto gptRequestDto) {
        return gptApiClient.sendPrompt(gptRequestDto)
                .doOnNext(raw -> log.info("[🧪 GPT Raw Response]: {}", raw))
                .map(GptResponseParser::extractFieldsFromResponse)
                .doOnNext(parsed -> {
                    if (parsed == null || parsed.isEmpty()) {
                        log.error("[❌ GPT Parsing Failed!]: 파싱 결과가 비어 있습니다.");
                        throw new GptAnalysisException("GPT 응답 파싱 실패");
                    }
                })
                .map(parsed -> GptResponseParser.toDto(parsed, keysConfig))
                .onErrorResume(e -> {
                    log.error("[❌ GPT Analysis Error]: {}", e.getMessage(), e);
                    return Mono.error(new GptAnalysisException("GPT 분석 중 오류 발생", e));
                });
    }
}