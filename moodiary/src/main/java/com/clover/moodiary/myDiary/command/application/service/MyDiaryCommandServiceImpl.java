package com.clover.moodiary.myDiary.command.application.service;

import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.*;
import com.clover.moodiary.myDiary.command.domain.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class MyDiaryCommandServiceImpl implements MyDiaryCommandService {

    private final MyDiaryRepository myDiaryRepository;
    private final TagRepository tagRepository;
    private final MyDiaryTagRepository myDiaryTagRepository;
    private final MoodlogRepository moodlogRepository;

    @Autowired
    public MyDiaryCommandServiceImpl(MyDiaryRepository myDiaryRepository,
                                     TagRepository tagRepository,
                                     MyDiaryTagRepository myDiaryTagRepository,
                                     MoodlogRepository moodlogRepository) {
        this.myDiaryRepository = myDiaryRepository;
        this.tagRepository = tagRepository;
        this.myDiaryTagRepository = myDiaryTagRepository;
        this.moodlogRepository = moodlogRepository;
    }

    @Transactional
    @Override
    public void registDiary(MyDiaryCommandDTO dto) {
        LocalDate createdDate = dto.getCreatedAt().toLocalDate();
        boolean exists = myDiaryRepository.findByCreatedDateAndUserId(createdDate, dto.getUserId()).isPresent();

        if (exists) {
            log.warn("이미 해당 날짜에 작성된 일기가 있습니다. - 날짜: {}, 유저ID: {}", createdDate, dto.getUserId());
            throw new IllegalStateException("이미 오늘 날짜에 작성된 일기가 존재합니다.");
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

    @Transactional
    @Override
    public void saveMoodlog(MoodlogDTO dto) {
        LocalDate month = LocalDate.now().withDayOfMonth(1);

        boolean exists = moodlogRepository.existsByUserIdAndMonth(dto.getUserId(), month);
        if (exists) {
            log.warn("이미 등록된 moodlog가 있습니다. - 유저ID: {}, 월: {}", dto.getUserId(), month);
            throw new IllegalStateException("이미 이번 달의 moodlog가 존재합니다.");
        }

        MoodlogEntity moodlog = MoodlogEntity.builder()
                .userId(dto.getUserId())
                .content(dto.getContent())
                .month(month)
                .build();

        moodlogRepository.save(moodlog);
        log.info("Moodlog 저장 완료 - ID: {}, 유저ID: {}, 월: {}", moodlog.getId(), dto.getUserId(), month);
    }
}