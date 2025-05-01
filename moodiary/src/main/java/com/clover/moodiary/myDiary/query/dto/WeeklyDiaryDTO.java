package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeeklyDiaryDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String isConfirmed;
    private String styleLayer;
    private Integer totalScore;
}
