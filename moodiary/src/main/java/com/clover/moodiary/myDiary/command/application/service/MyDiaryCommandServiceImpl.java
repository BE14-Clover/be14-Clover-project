package com.clover.moodiary.myDiary.command.application.service;

import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryEntity;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryTagEntity;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryTagId;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.TagEntity;
import com.clover.moodiary.myDiary.command.domain.repository.EmotionAnalysisRepository;
import com.clover.moodiary.myDiary.command.domain.repository.MyDiaryRepository;
import com.clover.moodiary.myDiary.command.domain.repository.MyDiaryTagRepository;
import com.clover.moodiary.myDiary.command.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
public class MyDiaryCommandServiceImpl implements MyDiaryCommandService {

    private final MyDiaryRepository myDiaryRepository;
    private final TagRepository tagRepository;
    private final MyDiaryTagRepository myDiaryTagRepository;

    @Autowired
    public MyDiaryCommandServiceImpl(MyDiaryRepository myDiaryRepository,
                                     TagRepository tagRepository,
                                     MyDiaryTagRepository myDiaryTagRepository) {
        this.myDiaryRepository = myDiaryRepository;
        this.tagRepository = tagRepository;
        this.myDiaryTagRepository = myDiaryTagRepository;
    }

    @Transactional
    @Override
    public void registDiary(MyDiaryCommandDTO dto) {

        LocalDateTime createdAt = dto.getCreatedAt();
        boolean exists = myDiaryRepository.findByCreatedDateAndUserId(createdAt, dto.getUserId()).isPresent();

        if (exists) {
            log.warn("이미 해당 날짜에 작성된 일기가 있습니다. - 날짜: {}, 유저ID: {}", createdAt.toLocalDate(), dto.getUserId());
            throw new IllegalStateException("이미 해당 날짜에 작성된 일기가 존재합니다.");
        }

        MyDiaryEntity diary = MyDiaryEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .isDeleted(dto.getIsDeleted())
                .isConfirmed(dto.getIsConfirmed())
                .styleLayer(dto.getStyleLayer())
                .userId(dto.getUserId())
                .build();

        myDiaryRepository.save(diary);
        log.info("일기 저장 완료 - ID: {}", diary.getId());

        for (String tagName : dto.getTags()) {
            TagEntity tag = tagRepository.findByTagName(tagName)
                    .orElseGet(() -> {
                        TagEntity newTag = TagEntity.builder().tagName(tagName).build();
                        return tagRepository.save(newTag);
                    });

            MyDiaryTagEntity diaryTag = MyDiaryTagEntity.builder()
                    .id(new MyDiaryTagId(diary.getId(), tag.getId()))
                    .myDiary(diary)
                    .tag(tag)
                    .build();

            myDiaryTagRepository.save(diaryTag);
            log.info("태그 연결 저장 - DiaryID: {}, TagID: {}", diary.getId(), tag.getId());
        }
    }
}