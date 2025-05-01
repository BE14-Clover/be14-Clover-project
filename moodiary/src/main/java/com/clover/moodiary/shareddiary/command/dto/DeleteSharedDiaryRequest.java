package com.clover.moodiary.shareddiary.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteSharedDiaryRequest {
    private Integer diaryId;
    private Integer userId;
}
