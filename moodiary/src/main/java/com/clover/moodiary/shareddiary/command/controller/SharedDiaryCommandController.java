package com.clover.moodiary.shareddiary.command.controller;

import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryRequest;
import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryResponse;
import com.clover.moodiary.shareddiary.command.service.SharedDiaryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shareddiary")
@RequiredArgsConstructor
public class SharedDiaryCommandController {

    private final SharedDiaryCommandService sharedDiaryCommandService;

    @PostMapping("/create")
    public ResponseEntity<CreateSharedDiaryResponse> createDiary(@RequestBody CreateSharedDiaryRequest request) {
        CreateSharedDiaryResponse response = sharedDiaryCommandService.createDiary(request);
        return ResponseEntity.ok(response);
    }
}
