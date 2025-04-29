package com.clover.moodiary.gptapi.service;

import com.clover.moodiary.gptapi.command.dto.GptRequestDto;
import com.clover.moodiary.gptapi.command.dto.GptResponseDto;
import com.clover.moodiary.gptapi.external.GptApiClient;
// import com.clover.moodiary.gptapi.service.GptResponseParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptAnalysisService {

    private final GptApiClient gptApiClient;

    public Mono<GptResponseDto> analyzeDiary(GptRequestDto gptRequestDto) {
        return gptApiClient.sendPrompt(gptRequestDto)
                .flatMap(response -> {
                    // 1. GPT 응답 로깅
                    log.info("[GPT Raw Response] {}", response);

                    // 2. Content 파싱해서 Map 추출
                    Map<String, String> parsedMap = GptResponseParser.extractFieldsFromResponse(response);

                    if (parsedMap == null || parsedMap.isEmpty()) {
                        log.error("[GPT Parsing Failed] Response: {}", response);
                        return Mono.error(new RuntimeException("GPT 응답 파싱 실패"));
                    }

                    // 3. Map을 DTO로 변환
                    GptResponseDto responseDto = GptResponseParser.toDto(parsedMap);

                    // 4. 최종 DTO 반환
                    return Mono.just(responseDto);
                })
                .onErrorResume(e -> {
                    log.error("[GPT Analysis Error] {}", e.getMessage());
                    return Mono.error(new RuntimeException("GPT 분석 중 오류 발생", e));
                });
    }
}