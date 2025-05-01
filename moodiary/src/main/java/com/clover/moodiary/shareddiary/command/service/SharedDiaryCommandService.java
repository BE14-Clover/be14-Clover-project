package com.clover.moodiary.shareddiary.command.service;

import com.clover.moodiary.shareddiary.command.dto.*;

public interface SharedDiaryCommandService {
    CreateSharedDiaryResponse createDiary(CreateSharedDiaryRequest request);

    UpdateSharedDiaryReponse updateDiary(UpdateSharedDiaryRequest request);

    DeleteSharedDiaryResponse deleteDiary(DeleteSharedDiaryRequest request);
}
