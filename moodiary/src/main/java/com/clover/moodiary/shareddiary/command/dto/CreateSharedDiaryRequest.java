package com.clover.moodiary.shareddiary.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSharedDiaryRequest {
    private Integer roomId;
    private Integer userId;
    private String title;
    private String content;
    private String styleLayer;
}
