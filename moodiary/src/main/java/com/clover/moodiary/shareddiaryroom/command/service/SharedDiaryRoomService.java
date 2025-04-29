package com.clover.moodiary.shareddiaryroom.command.service;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;

public interface SharedDiaryRoomService {
    CreateSharedDiaryRoomResponse createRoom(CreateSharedDiaryRoomRequest request);
}
