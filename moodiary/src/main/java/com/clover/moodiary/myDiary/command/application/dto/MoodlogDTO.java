package com.clover.moodiary.myDiary.command.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MoodlogDTO {
    private Integer userId;
    private String content;
}