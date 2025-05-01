package com.clover.moodiary.myDiary.command.application.service;

import com.clover.moodiary.myDiary.command.application.dto.EmotionAnalysisDTO;
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
    private final EmotionAnalysisRepository emotionAnalysisRepository;

    @Autowired
    public MyDiaryCommandServiceImpl(MyDiaryRepository myDiaryRepository,
                                     TagRepository tagRepository,
                                     MyDiaryTagRepository myDiaryTagRepository,
                                     MoodlogRepository moodlogRepository,
                                     EmotionAnalysisRepository emotionAnalysisRepository
    ) {
        this.myDiaryRepository = myDiaryRepository;
        this.tagRepository = tagRepository;
        this.myDiaryTagRepository = myDiaryTagRepository;
        this.moodlogRepository = moodlogRepository;
        this.emotionAnalysisRepository = emotionAnalysisRepository;
    }

    @Transactional
    @Override
    public void registDiary(MyDiaryCommandDTO dto) {
        LocalDate createdDate = dto.getCreatedAt().toLocalDate();

        boolean existsNotDeleted = myDiaryRepository.existsByCreatedAtAndUserIdAndIsDeleted(
                dto.getCreatedAt(), dto.getUserId(), "N");

        if (existsNotDeleted) {
            String message = "이미 등록된 일기가 있습니다.";
            log.warn(message);
            throw new IllegalStateException(message);
        }

        MyDiaryEntity diary = MyDiaryEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .isDeleted("N") // 기본값 'N'
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
                    log.info("기존 moodlog 수정 - ID: {}, userId: {}, month: {}",
                            existing.getId(), dto.getUserId(), month);
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
        // ✅ isDeleted='N'인 일기만 찾아서 수정
        MyDiaryEntity diary = myDiaryRepository.findByIdAndIsDeleted(dto.getId(), "N")
                .orElseThrow(() -> new EntityNotFoundException("수정 가능한 일기를 찾을 수 없습니다. ID: " + dto.getId()));

        log.info("일기 수정 시작 - ID: {}", diary.getId());

        diary.setTitle(dto.getTitle());
        diary.setContent(dto.getContent());
        diary.setStyleLayer(dto.getStyleLayer());
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
        MyDiaryEntity diary = myDiaryRepository.findByIdAndIsDeleted(diaryId, "N")
                .orElseThrow(() -> new EntityNotFoundException("삭제 가능한 일기를 찾을 수 없습니다. ID: " + diaryId));

        myDiaryTagRepository.deleteByDiaryId(diaryId);
        log.info("태그 매핑 삭제 완료 - diaryId: {}", diaryId);

        // ✅ emotionAnalysisRepository.delete() 호출 제거!
        if (diary.getEmotionAnalysis() != null) {
            diary.setEmotionAnalysis(null); // orphanRemoval=true 에 의해 자동 삭제
            log.info("감정 분석 연결 해제 - diaryId: {}", diaryId);
        }

        diary.setIsDeleted("Y");
        myDiaryRepository.save(diary); // flush 시 cascade + orphanRemoval에 의해 emotionAnalysis 삭제됨
        log.info("일기 soft delete 완료 - diaryId: {}", diaryId);
    }

    // 감정 분석 저장
    public void saveEmotionAnalysis(EmotionAnalysisDTO dto) {
        if (dto == null || dto.getMyDiaryId() == null) {
            throw new IllegalArgumentException("Emotion data or Diary ID must not be null");
        }

        log.info("== 감정 분석 저장 요청 도착 ==");
        log.info("positiveScore: {}", dto.getPositiveScore());
        log.info("neutralScore: {}", dto.getNeutralScore());
        log.info("negativeScore: {}", dto.getNegativeScore());
        log.info("totalScore: {}", dto.getTotalScore());
        log.info("emotionSummary1: {}", dto.getEmotionSummary1());
        log.info("emotionSummary2: {}", dto.getEmotionSummary2());
        log.info("emotionSummary3: {}", dto.getEmotionSummary3());
        log.info("myDiarySummary: {}", dto.getMyDiarySummary());
        log.info("myDiaryId: {}", dto.getMyDiaryId());

        MyDiaryEntity diary = myDiaryRepository.findById(dto.getMyDiaryId())
                .orElseThrow(() -> new EntityNotFoundException("일기 ID가 존재하지 않음: " + dto.getMyDiaryId()));

        EmotionAnalysisEntity entity = EmotionAnalysisEntity.builder()
                .totalScore(dto.getTotalScore())
                .positiveScore(dto.getPositiveScore())
                .neutralScore(dto.getNeutralScore())
                .negativeScore(dto.getNegativeScore())
                .emotionSummary1(dto.getEmotionSummary1())
                .emotionSummary2(dto.getEmotionSummary2())
                .emotionSummary3(dto.getEmotionSummary3())
                .myDiarySummary(dto.getMyDiarySummary())
                .myDiaryEntity(diary)
                .build();

        emotionAnalysisRepository.save(entity);
    }

}
