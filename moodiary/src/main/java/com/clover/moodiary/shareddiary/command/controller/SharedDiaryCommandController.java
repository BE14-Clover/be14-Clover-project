package com.clover.moodiary.shareddiary.command.controller;

import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryRequest;
import com.clover.moodiary.shareddiary.command.dto.CreateSharedDiaryResponse;
import com.clover.moodiary.shareddiary.command.dto.UpdateSharedDiaryReponse;
import com.clover.moodiary.shareddiary.command.dto.UpdateSharedDiaryRequest;
import com.clover.moodiary.shareddiary.command.service.SharedDiaryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<UpdateSharedDiaryReponse> updateDiary(@RequestBody UpdateSharedDiaryRequest request) {
        UpdateSharedDiaryReponse response = sharedDiaryCommandService.updateDiary(request);
        return ResponseEntity.ok(response);
    }
}
