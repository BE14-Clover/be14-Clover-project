package com.clover.moodiary.myDiary.command.application.controller;

import com.clover.moodiary.global.util.S3Uploader;
import com.clover.moodiary.myDiary.command.application.dto.EmotionAnalysisDTO;
import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.service.MyDiaryCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/mydiary")
public class MyDiaryCommandController {

    private final MyDiaryCommandService myDiaryCommandService;
    private final S3Uploader s3Uploader;

    @Autowired
    public MyDiaryCommandController(MyDiaryCommandService myDiaryCommandService, S3Uploader s3Uploader) {
        this.myDiaryCommandService = myDiaryCommandService;
        this.s3Uploader = s3Uploader;
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(
            @RequestPart("dto") MyDiaryCommandDTO myDiaryCommandDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer)auth.getPrincipal();

        myDiaryCommandDTO.setUserId(userId);

        try {
            myDiaryCommandService.registDiary(myDiaryCommandDTO, image);
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3Uploader.upload(file);
            return ResponseEntity.ok().body(Map.of("url", imageUrl));
        } catch (IOException e) {
            log.error("S3 업로드 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 실패");
        }
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable("diaryId") Integer diaryId) {
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