package com.clover.moodiary.myDiary.command.application.controller;


import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.service.MyDiaryCommandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
//            // 현재 인증된 사용자 정보 가져오기
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
//            log.info("일기 수정 요청 - 유저: {}, DTO: {}", username, myDiaryCommandDTO);

            // 서비스 호출
            myDiaryCommandService.updateDiary(myDiaryCommandDTO);

            return ResponseEntity.ok("일기 수정 완료");
        } catch (EntityNotFoundException e) {
            log.warn("수정 실패 - 일기 없음. 세부 사항: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 일기를 찾을 수 없습니다.");
        } catch (Exception e) {
            log.error("일기 수정 중 서버 측 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    @PostMapping("/moodlog")
    public ResponseEntity<?> registMoodlog(@RequestBody MoodlogDTO moodlogDTO) {
        myDiaryCommandService.saveMoodlog(moodlogDTO);
        return ResponseEntity.ok("Moodlog 등록이 완료되었습니다.");
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable Integer diaryId) {
        try {
            myDiaryCommandService.deleteDiary(diaryId);
            return ResponseEntity.ok("일기 삭제 완료");
        } catch (EntityNotFoundException e) {
            log.warn("삭제 실패 - 존재하지 않는 일기: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 일기를 찾을 수 없습니다.");
        } catch (Exception e) {
            log.error("일기 삭제 중 서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }



}