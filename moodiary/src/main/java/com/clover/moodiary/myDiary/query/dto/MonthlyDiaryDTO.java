package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MonthlyDiaryDTO {
    private int id;
    private String title;
    private LocalDateTime createdAt;
    private String isConfirmed;
    private int positiveScore;
    private int neutralScore;
    private int negativeScore;
    private int totalScore;
    private String emotionSummary1;
    private String emotionSummary2;
    private String emotionSummary3;
}