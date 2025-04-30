package com.clover.moodiary.shareddiaryroom.query.service;

import com.clover.moodiary.shareddiaryroom.query.dto.SharedDiaryRoomResponse;

import java.util.List;

public interface SharedDiaryRoomQueryService {

    List<SharedDiaryRoomResponse> findRoomByUserId(Integer userId);
}
