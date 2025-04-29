package com.clover.moodiary.shareddiaryroom.query.service;

import com.clover.moodiary.shareddiaryroom.query.dto.SharedDiaryRoomResponse;

import java.util.List;

public interface SharedDiaryRoomService {

    List<SharedDiaryRoomResponse> findRoomByUserId(Integer userId);
}
