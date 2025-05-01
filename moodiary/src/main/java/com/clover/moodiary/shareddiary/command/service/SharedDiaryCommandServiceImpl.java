package com.clover.moodiary.shareddiary.command.service;

import com.clover.moodiary.global.util.S3Uploader;
import com.clover.moodiary.shareddiary.command.dto.*;
import com.clover.moodiary.shareddiary.command.entity.SharedDiary;
import com.clover.moodiary.shareddiary.command.repository.SharedDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SharedDiaryCommandServiceImpl implements SharedDiaryCommandService {

    private final SharedDiaryRepository sharedDiaryRepository;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public CreateSharedDiaryResponse createDiary(CreateSharedDiaryRequest request, MultipartFile image) {
        String finalStyleLayer = request.getStyleLayer(); // 기본 스타일 레이어 (스티커만 있는 상태)

        if (image != null && !image.isEmpty()) {
            String imageUrl = null;
            try {
                imageUrl = s3Uploader.upload(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 이미지 레이어 객체를 JSON으로 생성
            String imageLayerJson = String.format(
                    "{\"type\":\"image\",\"src\":\"%s\",\"x\":0,\"y\":0,\"width\":200,\"height\":200,\"rotation\":0}",
                    imageUrl
            );

            // styleLayer가 null 또는 빈 배열이면 새로 생성
            if (finalStyleLayer == null || finalStyleLayer.trim().isEmpty() || finalStyleLayer.trim().equals("[]")) {
                finalStyleLayer = "[" + imageLayerJson + "]";
            } else {
                // 기존 배열 끝에 , 추가해서 붙임
                finalStyleLayer = finalStyleLayer.replaceAll("\\]$", "") + "," + imageLayerJson + "]";
            }
        }

        SharedDiary diary = new SharedDiary();
        diary.setSharedDiaryRoomId(request.getRoomId());
        diary.setUserId(request.getUserId());
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setStyleLayer(finalStyleLayer); // 이미지가 포함된 styleLayer 저장

        diary.setCreatedAt(LocalDateTime.now());
        diary.setIsDeleted("N");
        diary.setFixedState("Y");

        SharedDiary saved = sharedDiaryRepository.save(diary);
        return new CreateSharedDiaryResponse(saved.getId());
    }

    @Override
    @Transactional
    public UpdateSharedDiaryReponse updateDiary(UpdateSharedDiaryRequest request, MultipartFile image) {
        SharedDiary diary = sharedDiaryRepository.findById(request.getDiaryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기입니다."));

        if(!diary.getUserId().equals(request.getUserId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        String finalStyleLayer = request.getStyleLayer();

        if (image != null && !image.isEmpty()) {
            String imageUrl = null;
            try {
                imageUrl = s3Uploader.upload(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String imageLayerJson = String.format(
                    "{\"type\":\"image\",\"src\":\"%s\",\"x\":0,\"y\":0,\"width\":200,\"height\":200,\"rotation\":0}",
                    imageUrl
            );

            // styleLayer가 null 또는 빈 배열이면 새로 생성
            if (finalStyleLayer == null || finalStyleLayer.trim().isEmpty() || finalStyleLayer.trim().equals("[]")) {
                finalStyleLayer = "[" + imageLayerJson + "]";
            } else {
                // 기존 배열 끝에 , 추가해서 붙임
                finalStyleLayer = finalStyleLayer.replaceAll("\\]$", "") + "," + imageLayerJson + "]";
            }
        }

        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setStyleLayer(finalStyleLayer);

        return new UpdateSharedDiaryReponse(diary.getId());
    }

    @Override
    @Transactional
    public DeleteSharedDiaryResponse deleteDiary(DeleteSharedDiaryRequest request) {
        SharedDiary diary = sharedDiaryRepository.findById(request.getDiaryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 일기입니다."));

        diary.setIsDeleted("Y");

        return new DeleteSharedDiaryResponse(diary.getId());
    }


}
