package com.clover.moodiary.shareddiary.command.dto;

import lombok.Getter;

@Getter
public class CreateSharedDiaryRequest {
    private Integer roomId;
    private Integer userId;
    private String title;
    private String content;
    private String styleLayer;
}
