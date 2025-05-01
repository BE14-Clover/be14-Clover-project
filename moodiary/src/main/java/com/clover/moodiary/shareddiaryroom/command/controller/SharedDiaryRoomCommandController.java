package com.clover.moodiary.shareddiaryroom.command.controller;

import com.clover.moodiary.shareddiaryroom.command.dto.CreateSharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.command.service.SharedDiaryRoomCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/shareddiaryroom")
@RequiredArgsConstructor
public class SharedDiaryRoomCommandController {

    private final SharedDiaryRoomCommandService sharedDiaryRoomCommandService;

    @PostMapping("/create")
    public CreateSharedDiaryRoomResponse createRoom() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer)auth.getPrincipal();
        return sharedDiaryRoomCommandService.createRoom(userId);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterRoom(@RequestBody Map<String, Integer> request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer)auth.getPrincipal();
        Integer roomId = request.get("roomId");
        sharedDiaryRoomCommandService.enterRoom(roomId, userId);
        return ResponseEntity.ok("입장 완료");
    }
}
