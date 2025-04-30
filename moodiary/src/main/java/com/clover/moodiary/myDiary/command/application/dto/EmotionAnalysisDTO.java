package com.clover.moodiary.myDiary.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmotionAnalysisDTO {
    private Integer id;
    private Integer positiveScore;
    private Integer neutralScore;
    private Integer negativeScore;
    private Integer totalScore;
    private String emotionalSummary1;
    private String emotionalSummary2;
    private String emotionalSummary3;
    private String diarySummary;

    private Integer myDiaryId;

}
