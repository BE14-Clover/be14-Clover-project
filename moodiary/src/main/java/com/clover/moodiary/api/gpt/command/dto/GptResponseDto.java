package com.clover.moodiary.api.gpt.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GptResponseDto {

    private int positiveScore; // 긍정 감정 점수
    private int neutralScore; // 보통 감정 점수
    private int negativeScore; // 부정 감정 점수
    private int totalScore; // 총합 감정 점수

    private String emotion1; // 대표 감정 1
    private String emotion2; // 대표 감정 2
    private String emotion3; // 대표 감정 3

    private String diaryTitle; // 일기 제목
}