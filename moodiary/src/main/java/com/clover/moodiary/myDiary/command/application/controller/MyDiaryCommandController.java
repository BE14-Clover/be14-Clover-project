package com.clover.moodiary.myDiary.command.application.controller;

import com.clover.moodiary.myDiary.command.application.dto.EmotionAnalysisDTO;
import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.service.MyDiaryCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/mydiary")
public class MyDiaryCommandController {

    private final MyDiaryCommandService myDiaryCommandService;

    @Autowired
    public MyDiaryCommandController(MyDiaryCommandService myDiaryCommandService) {
        this.myDiaryCommandService = myDiaryCommandService;
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody MyDiaryCommandDTO myDiaryCommandDTO) {
        try {
            myDiaryCommandService.registDiary(myDiaryCommandDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("일기 등록 완료");
        } catch (IllegalStateException e) {
            log.warn("일기 등록 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody MyDiaryCommandDTO myDiaryCommandDTO) {
        try {
            myDiaryCommandService.updateDiary(myDiaryCommandDTO);
            return ResponseEntity.ok("일기 수정 완료");
        } catch (EntityNotFoundException e) {
            log.warn("수정 실패 - 수정 가능한 일기 없음: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정 가능한 일기를 찾을 수 없습니다.");
        } catch (Exception e) {
            log.error("일기 수정 중 서버 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable(value = "diaryId") Integer diaryId) {
        try {
            myDiaryCommandService.deleteDiary(diaryId);
            return ResponseEntity.ok("일기 삭제(소프트 딜리트) 완료");
        } catch (EntityNotFoundException e) {
            log.warn("삭제 실패 - 삭제 가능한 일기 없음: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제 가능한 일기를 찾을 수 없습니다.");
        } catch (Exception e) {
            log.error("일기 삭제 중 서버 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    @PostMapping("/registEmotion")
    public ResponseEntity<?> registEmotion(@RequestBody EmotionAnalysisDTO emotionAnalysisDTO) {
        try {
            myDiaryCommandService.saveEmotionAnalysis(emotionAnalysisDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            log.warn("요청 데이터 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            log.warn("감정 분석 저장 실패 - 대상 일기 없음: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("감정 분석 저장 중 서버 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("감정 분석 저장 중 오류 발생");
        }
    }

    @PostMapping("/moodlog")
    public ResponseEntity<?> registMoodlog(@RequestBody MoodlogDTO moodlogDTO) {
        myDiaryCommandService.saveMoodlog(moodlogDTO);
        return ResponseEntity.ok("Moodlog 등록 완료");
    }
}

