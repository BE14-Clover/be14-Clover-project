package com.clover.moodiary.shareddiaryroom.command.dto;

import lombok.Getter;

@Getter
public class EnterSharedDiaryRoomRequest {
    private Integer roomId;
    private Integer userId;
}
