package com.clover.moodiary.shareddiaryroom.command.controller;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.command.dto.EnterSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.service.SharedDiaryRoomCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shareddiaryroom")
@RequiredArgsConstructor
public class SharedDiaryRoomCommandController {

    private final SharedDiaryRoomCommandService sharedDiaryRoomCommandService;

    @PostMapping("/create")
    public CreateSharedDiaryRoomResponse createRoom(@RequestBody CreateSharedDiaryRoomRequest request) {
        return sharedDiaryRoomCommandService.createRoom(request);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterRoom(@RequestBody EnterSharedDiaryRoomRequest request) {
        sharedDiaryRoomCommandService.enterRoom(request);
        return ResponseEntity.ok("입장 완료");
    }
}
