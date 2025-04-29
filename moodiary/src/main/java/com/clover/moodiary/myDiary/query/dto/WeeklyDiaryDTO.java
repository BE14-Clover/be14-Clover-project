package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeeklyDiaryDTO {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String isConfirmed;
    private int totalScore;
}