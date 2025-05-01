package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MonthlyDiaryDTO {
    private Integer id;
    private String title;
    private LocalDateTime createdAt;
    private String isConfirmed;
    private String styleLayer;
    private Integer positiveScore;
    private Integer neutralScore;
    private Integer negativeScore;
    private Integer totalScore;
    private String emotionSummary1;
    private String emotionSummary2;
    private String emotionSummary3;
}
