package com.clover.moodiary.shareddiary.command.service;

import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryRequest;
import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryResponse;

public interface SharedDiaryCommandService {
    CreateSharedDiaryResponse createDiary(CreateSharedDiaryRequest request);
}
