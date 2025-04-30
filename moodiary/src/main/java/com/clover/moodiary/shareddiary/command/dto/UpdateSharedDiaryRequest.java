package com.clover.moodiary.shareddiary.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateSharedDiaryRequest {
    private Integer diaryId;
    private Integer userId;
    private String title;
    private String content;
    private String styleLayer;
}
