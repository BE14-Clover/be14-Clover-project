package com.clover.moodiary.shareddiary.command.service;

import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryRequest;
import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryResponse;
import com.clover.moodiary.shareddiary.command.dto.UpdateSharedDiaryReponse;
import com.clover.moodiary.shareddiary.command.dto.UpdateSharedDiaryRequest;

public interface SharedDiaryCommandService {
    CreateSharedDiaryResponse createDiary(CreateSharedDiaryRequest request);

    UpdateSharedDiaryReponse updateDiary(UpdateSharedDiaryRequest request);
}
