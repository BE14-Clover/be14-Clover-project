package com.clover.moodiary.shareddiaryroom.command.controller;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomRequest;
import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.command.service.SharedDiaryRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shareddiaryroom")
@RequiredArgsConstructor
public class SharedDiaryRoomController {

    private final SharedDiaryRoomService sharedDiaryRoomService;

    @PostMapping
    public CreateSharedDiaryRoomResponse createRoom(@RequestBody CreateSharedDiaryRoomRequest request) {
        return sharedDiaryRoomService.createRoom(request);
    }
}
