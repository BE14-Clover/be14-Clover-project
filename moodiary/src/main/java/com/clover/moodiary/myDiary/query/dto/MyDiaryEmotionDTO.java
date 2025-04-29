package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyDiaryEmotionDTO {
    private int id;
    private int positiveScore;
    private int neutralScore;
    private int negativeScore;
    private int totalScore;
    private String emotionSummary1;
    private String emotionSummary2;
    private String emotionSummary3;
    private String diarySummary;

    private int diaryId;
}
