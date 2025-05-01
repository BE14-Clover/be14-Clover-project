package com.clover.moodiary.shareddiaryroom.query.service;

import com.clover.moodiary.shareddiaryroom.query.dto.SharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.query.mapper.SharedDiaryRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedDiaryRoomServiceQueryImpl implements SharedDiaryRoomQueryService {

    private final SharedDiaryRoomMapper sharedDiaryRoomMapper;

    @Autowired
    public SharedDiaryRoomServiceQueryImpl(SharedDiaryRoomMapper sharedDiaryRoomMapper) {
        this.sharedDiaryRoomMapper = sharedDiaryRoomMapper;
    }

    @Override
    public List<SharedDiaryRoomResponse> findRoomByUserId(Integer userId) {
        return sharedDiaryRoomMapper.findRoomByUserId(userId);
    }
}
