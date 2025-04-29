package com.clover.moodiary.shareddiary.query.service;

import com.clover.moodiary.shareddiary.query.dto.SharedDiaryResponse;
import com.clover.moodiary.shareddiary.query.mapper.SharedDiaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedDiaryServiceImpl implements SharedDiaryService {

    private final SharedDiaryMapper sharedDiaryMapper;

    @Autowired
    public SharedDiaryServiceImpl(SharedDiaryMapper sharedDiaryMapper) {
        this.sharedDiaryMapper = sharedDiaryMapper;
    }

    @Override
    public List<SharedDiaryResponse> findDiaryByRoomId(Integer roomId) {
        return sharedDiaryMapper.findDiaryByRoomId(roomId);
    }
}
