package com.clover.moodiary.gptapi.service;

import com.clover.moodiary.gptapi.command.dto.GptRequestDto;
import com.clover.moodiary.gptapi.external.GptApiClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GptAnalysisService {
    private final GptApiClient gptApiClient;

    public Mono<String> analyzeDiary(GptRequestDto gptRequestDto) {
        return gptApiClient.sendPrompt(gptRequestDto)
            .doOnNext(result -> {
                // 로그 찍기
                System.out.println("GPT 결과: " + result);
            });
    }
}

// @Service
// @RequiredArgsConstructor
// public class GptAnalysisService {

//     private final GptApiClient gptApiClient;
//     private static final Logger logger = LoggerFactory.getLogger(GptAnalysisService.class);

//     /**
//      * 일기 분석을 위해 GPT API에 요청을 보내는 메서드
//      * 
//      * @param gptRequestDto GPT 요청 데이터를 담은 DTO
//      * @return Mono<String> 비동기적으로 처리된 GPT 응답
//      */
//     public Mono<String> analyzeDiary(GptRequestDto gptRequestDto) {
//         // 요청 데이터 로깅
//         logger.info("GPT 분석 요청: {}", gptRequestDto);

//         return gptApiClient.sendPrompt(gptRequestDto)
//                 .doOnTerminate(() -> logger.info("GPT 요청 처리 완료"))
//                 .onErrorResume(e -> {
//                     // 오류 처리 및 로깅
//                     logger.error("GPT 요청 실패: {}", e.getMessage());
//                     return Mono.empty(); // 오류 발생 시 빈 Mono 반환
//                 });
//     }
// }