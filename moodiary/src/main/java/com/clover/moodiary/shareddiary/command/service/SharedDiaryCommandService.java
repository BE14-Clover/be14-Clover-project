package com.clover.moodiary.shareddiary.command.service;

import com.clover.moodiary.shareddiary.command.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface SharedDiaryCommandService {
    CreateSharedDiaryResponse createDiary(CreateSharedDiaryRequest request, MultipartFile image);

    UpdateSharedDiaryReponse updateDiary(UpdateSharedDiaryRequest request, MultipartFile image);

    DeleteSharedDiaryResponse deleteDiary(DeleteSharedDiaryRequest request);
}
