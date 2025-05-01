package com.clover.moodiary.shareddiaryroom.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SharedDiaryRoomResponse {
    private Integer roomId;
    private String latestTitle;
    private String authorName;
    private LocalDateTime createdAt;
}
