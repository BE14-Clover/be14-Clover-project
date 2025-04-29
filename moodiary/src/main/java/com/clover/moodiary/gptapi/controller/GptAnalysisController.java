package com.clover.moodiary.gptapi.controller;

import com.clover.moodiary.gptapi.command.dto.GptRequestDto;
import com.clover.moodiary.gptapi.command.dto.GptResponseDto;
import com.clover.moodiary.gptapi.service.GptAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
public class GptAnalysisController {

        private final GptAnalysisService gptAnalysisService;

        // ✅ yml에서 model 읽어오기
        @Value("${openai.api.model}")
        private String model;

        @PostMapping("/analyze")
        public Mono<GptResponseDto> analyzeDiary(@RequestBody String diaryContent) {
                // ✅ 고정 system 프롬프트
                String systemPrompt = """
                                다음 텍스트의 감정을 분석하세요.

                                - 긍정, 보통, 부정 감정 점수를 100점 만점 기준으로 계산
                                - 총합 점수는 긍정적일수록 높게 계산
                                - 감정 요약은 [ 기쁨, 감사, 만족, 설렘, 자부심, 안도, 애정, 희망, 자신감, 평온, 재미, 호기심, 그리움, 기대, 몰입, 수용, 놀라움, 복합 감정, 분노, 슬픔, 좌절, 외로움, 불안, 질투, 수치심, 두려움, 실망, 지루함, 죄책감, 후회 ] 중 3개를 선택해 제시 (겹치지 않게)
                                - 일기 제목은 20대가 쓸만한 제목으로, 20자 이내로 작성 (필수)
                                - 반드시 아래 순서와 항목명을 지켜 반환하세요:

                                긍정 감정 점수:
                                보통 감정 점수:
                                부정 감정 점수:
                                총합 감정 점수:
                                대표 감정1:
                                대표 감정2:
                                대표 감정3:
                                일기 제목:
                                """;

                // ✅ GptRequestDto 조립
                GptRequestDto requestDto = new GptRequestDto(
                                model, // yml에서 읽어온 모델명 사용
                                List.of(
                                                new GptRequestDto.Message("system", systemPrompt),
                                                new GptRequestDto.Message("user", diaryContent)));

                return gptAnalysisService.analyzeDiary(requestDto);
        }
}