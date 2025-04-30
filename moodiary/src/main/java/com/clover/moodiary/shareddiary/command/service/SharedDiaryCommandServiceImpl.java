package com.clover.moodiary.shareddiary.command.service;

import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryRequest;
import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryResponse;
import com.clover.moodiary.shareddiary.command.entity.SharedDiary;
import com.clover.moodiary.shareddiary.command.repository.SharedDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SharedDiaryCommandServiceImpl implements SharedDiaryCommandService {

    private final SharedDiaryRepository sharedDiaryRepository;

    @Override
    @Transactional
    public CreateSharedDiaryResponse createDiary(CreateSharedDiaryRequest request) {
        SharedDiary diary = new SharedDiary();
        diary.setSharedDiaryRoomId((request.getRoomId()));
        diary.setUserId(request.getUserId());
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setStyleLayer(request.getStyleLayer());

        diary.setCreatedAt(LocalDateTime.now());
        diary.setIsDeleted("N");
        diary.setFixedState("Y");

        SharedDiary saved = sharedDiaryRepository.save(diary);
        return new CreateSharedDiaryResponse(saved.getId());
    }
}
