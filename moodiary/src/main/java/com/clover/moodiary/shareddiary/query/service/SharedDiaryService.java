package com.clover.moodiary.shareddiary.query.service;

import com.clover.moodiary.shareddiary.query.dto.SharedDiaryDetailResponse;
import com.clover.moodiary.shareddiary.query.dto.SharedDiaryResponse;

import java.util.List;

public interface SharedDiaryService {
    List<SharedDiaryResponse> findDiaryByRoomId(Integer roomId);

    SharedDiaryDetailResponse findDiaryById(Integer diaryId);
}
