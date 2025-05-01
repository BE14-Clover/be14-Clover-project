package com.clover.moodiary.shareddiary.command.controller;

import com.clover.moodiary.shareddiary.command.dto.*;
import com.clover.moodiary.shareddiary.command.service.SharedDiaryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shareddiary")
@RequiredArgsConstructor
public class SharedDiaryCommandController {

    private final SharedDiaryCommandService sharedDiaryCommandService;

    @PostMapping("/create")
    public ResponseEntity<CreateSharedDiaryResponse> createDiary(@RequestBody CreateSharedDiaryRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) auth.getPrincipal();

        CreateSharedDiaryRequest fixedRequest = new CreateSharedDiaryRequest(
                request.getRoomId(),
                userId,
                request.getTitle(),
                request.getContent(),
                request.getStyleLayer()
        );
        return ResponseEntity.ok(sharedDiaryCommandService.createDiary(fixedRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateSharedDiaryReponse> updateDiary(@RequestBody UpdateSharedDiaryRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) auth.getPrincipal();

        request = new UpdateSharedDiaryRequest(
                request.getDiaryId(),
                userId,
                request.getTitle(),
                request.getContent(),
                request.getStyleLayer()
        );
        return ResponseEntity.ok(sharedDiaryCommandService.updateDiary(request));
    }

    @PutMapping("/delete")
    public ResponseEntity<DeleteSharedDiaryResponse> deleteDiary(@RequestBody DeleteSharedDiaryRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) auth.getPrincipal();

        DeleteSharedDiaryRequest fixedRequest = new DeleteSharedDiaryRequest(
                request.getDiaryId(),
                userId
        );

        return ResponseEntity.ok(sharedDiaryCommandService.deleteDiary(fixedRequest));
    }

}
