package com.clover.moodiary.myDiary.command.application.service;

import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.*;
import com.clover.moodiary.myDiary.command.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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

        updateDiaryTags(diary, dto.getTags());
    }

    @Override
    @Transactional
    public void saveMoodlog(MoodlogDTO dto) {
        LocalDate month;
        try {
            month = LocalDate.parse(dto.getTargetMonth()).withDayOfMonth(1);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }

        MoodlogEntity moodlog = moodlogRepository.findByUserIdAndMonth(dto.getUserId(), month)
                .map(existing -> {
                    existing.setContent(dto.getContent());
                    log.info("기존 moodlog 수정 - ID: {}, userId: {}, month: {}", existing.getId(), dto.getUserId(), month);
                    return existing;
                })
                .orElseGet(() -> {
                    MoodlogEntity newMoodlog = MoodlogEntity.builder()
                            .userId(dto.getUserId())
                            .content(dto.getContent())
                            .month(month)
                            .build();
                    log.info("새 moodlog 등록 - userId: {}, month: {}", dto.getUserId(), month);
                    return newMoodlog;
                });

        moodlogRepository.save(moodlog);
    }

    @Transactional
    @Override
    public void updateDiary(MyDiaryCommandDTO dto) {
        MyDiaryEntity diary = myDiaryRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("ID: " + dto.getId() + "에 해당하는 일기가 없습니다."));

        log.info("일기 수정 시작 - 기존 데이터: {}", diary);

        diary.setTitle(dto.getTitle());
        diary.setContent(dto.getContent());
        diary.setStyleLayer(dto.getStyleLayer());
        diary.setIsDeleted(dto.getIsDeleted());
        diary.setIsConfirmed(dto.getIsConfirmed());
        diary.setCreatedAt(dto.getCreatedAt());

        myDiaryRepository.save(diary);
        log.info("일기 수정 완료 - ID: {}", diary.getId());

        updateDiaryTags(diary, dto.getTags());
    }

    @Transactional
    public void updateDiaryTags(MyDiaryEntity diary, List<String> newTags) {
        Integer diaryId = diary.getId();
        myDiaryTagRepository.deleteByDiaryId(diaryId);
        log.info("기존 태그 삭제 완료 - DiaryID: {}", diaryId);

        if (newTags == null || newTags.isEmpty()) {
            return;
        }

        for (String tagName : newTags) {
            TagEntity tag = tagRepository.findByTagName(tagName)
                    .orElseGet(() -> tagRepository.save(TagEntity.builder().tagName(tagName).build()));

            MyDiaryTagEntity diaryTag = MyDiaryTagEntity.builder()
                    .id(new MyDiaryTagId(diaryId, tag.getId()))
                    .myDiary(diary)
                    .tag(tag)
                    .build();

            myDiaryTagRepository.save(diaryTag);
            log.info("새 태그 저장 완료 - DiaryID: {}, TagName: {}", diaryId, tagName);
        }
    }


    @Transactional
    @Override
    public void deleteDiary(Integer diaryId) {
        MyDiaryEntity diary = myDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("일기 ID " + diaryId + " 없음"));

        try {
            myDiaryTagRepository.deleteByDiaryId(diaryId);
            log.info("태그 매핑 삭제 완료 - diaryId: {}", diaryId);

            myDiaryRepository.delete(diary);
            log.info("일기 및 감정 분석 삭제 완료 - diaryId: {}", diaryId);

        } catch (Exception e) {
            log.error("일기 삭제 중 예외 발생 - rollback 수행", e);
            throw e; // 롤백
        }
    }
}