package com.clover.moodiary.shareddiaryroom.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnterSharedDiaryRoomRequest {
    private Integer roomId;
    private Integer userId;
}
