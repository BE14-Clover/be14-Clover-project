package com.clover.moodiary.api.gpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.clover.moodiary.api.gpt.command.dto.GptRequestDto;
import com.clover.moodiary.api.gpt.command.dto.GptResponseDto;
import com.clover.moodiary.api.gpt.service.GptAnalysisService;

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
                                다음 텍스트의 감정을 분석하세요:

                                - 긍정 감정 점수, 보통 감정 점수, 부정 감정 점수를 100점 만점 기준으로 계산하세요.
                                - 총합 감정 점수는 긍정적일수록 높은 점수입니다(계산식 출력).
                                - 감정을 대표하는 주요 감정 3가지를 감정 요약으로 제시하세요.
                                - 일기 제목은 유저층인 20대가 쓸만한 제목으로, 20자 이내여야 하며 반드시 존재해야 합니다.
                                - 감정은 아래 리스트에서 중복 없이 3개만 선택하세요:

                                [ 기쁨, 감사, 만족, 설렘, 자부심, 안도, 애정, 희망, 자신감, 평온, 재미, 호기심, 그리움, 기대, 몰입, 수용, 놀라움, 복합 감정, 분노, 슬픔, 좌절, 외로움, 불안, 질투, 수치심, 두려움, 실망, 지루함, 죄책감, 후회 ]

                                - 아래 JSON 형식을 "정확하게 그대로" 반환하세요. 줄바꿈이나 key 이름 변경, 설명 없이 순수 JSON 객체만 반환해야 합니다.

                                {
                                        "긍정 감정 점수": 0,
                                        "보통 감정 점수": 0,
                                        "부정 감정 점수": 0,
                                        "총합 감정 점수": 0,
                                        "대표 감정1": "",
                                        "대표 감정2": "",
                                        "대표 감정3": "",
                                        "일기 제목": ""
                                }
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