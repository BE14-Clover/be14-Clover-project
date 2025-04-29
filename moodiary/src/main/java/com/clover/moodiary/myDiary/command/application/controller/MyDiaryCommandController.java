package com.clover.moodiary.myDiary.command.application.controller;


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
        myDiaryCommandService.registDiary(myDiaryCommandDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("일기 등록 완료");
    }





}
