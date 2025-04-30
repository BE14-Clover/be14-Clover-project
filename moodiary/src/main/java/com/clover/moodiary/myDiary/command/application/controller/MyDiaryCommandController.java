package com.clover.moodiary.myDiary.command.application.controller;


import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.service.MyDiaryCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/moodlog")
    public ResponseEntity<?> registMoodlog(@RequestBody MoodlogDTO moodlogDTO) {
        myDiaryCommandService.saveMoodlog(moodlogDTO);
        return ResponseEntity.ok("Moodlog 등록이 완료되었습니다.");
    }





}