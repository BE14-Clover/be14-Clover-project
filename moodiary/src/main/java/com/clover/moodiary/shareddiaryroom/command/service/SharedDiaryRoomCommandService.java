package com.clover.moodiary.shareddiaryroom.command.service;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;

public interface SharedDiaryRoomCommandService {
    CreateSharedDiaryRoomResponse createRoom(Integer userId);
    
    void enterRoom(Integer roomId, Integer userId);
}
