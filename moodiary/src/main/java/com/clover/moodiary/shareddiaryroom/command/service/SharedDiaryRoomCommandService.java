package com.clover.moodiary.shareddiaryroom.command.service;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.command.dto.EnterSharedDiaryRoomRequest;

public interface SharedDiaryRoomCommandService {
    CreateSharedDiaryRoomResponse createRoom(CreateSharedDiaryRoomRequest request);
    
    void enterRoom(EnterSharedDiaryRoomRequest request);
}
