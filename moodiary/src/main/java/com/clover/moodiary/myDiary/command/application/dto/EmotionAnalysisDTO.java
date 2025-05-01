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
    private String emotionSummary1;
    private String emotionSummary2;
    private String emotionSummary3;
    private String myDiarySummary;

    private Integer myDiaryId;

}

