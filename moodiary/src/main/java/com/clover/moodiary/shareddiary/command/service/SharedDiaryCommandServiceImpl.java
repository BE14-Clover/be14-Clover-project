package com.clover.moodiary.shareddiary.command.service;

import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryRequest;
import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryResponse;
import com.clover.moodiary.shareddiary.command.dto.UpdateSharedDiaryReponse;
import com.clover.moodiary.shareddiary.command.dto.UpdateSharedDiaryRequest;
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

    @Override
    @Transactional
    public UpdateSharedDiaryReponse updateDiary(UpdateSharedDiaryRequest request) {
        SharedDiary diary = sharedDiaryRepository.findById(request.getDiaryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기입니다."));

        if(!diary.getUserId().equals(request.getUserId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setStyleLayer(request.getStyleLayer());

        return new UpdateSharedDiaryReponse(diary.getId());
    }


}
