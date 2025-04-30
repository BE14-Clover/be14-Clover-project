package com.clover.moodiary.myDiary.command.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodlogDTO {
    private Integer userId;
    private String content;
    private String targetMonth;
}