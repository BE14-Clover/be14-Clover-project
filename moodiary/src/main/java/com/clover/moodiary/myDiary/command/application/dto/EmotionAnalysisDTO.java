package com.clover.moodiary.myDiary.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmotionAnalysisDTO {
    private int id;
    private int positiveScore;
    private int neutralScore;
    private int negativeScore;
    private int totalScore;
    private String emotionalSummary1;
    private String emotionalSummary2;
    private String emotionalSummary3;
    private String diarySummary;

    private int myDiaryId;

}
