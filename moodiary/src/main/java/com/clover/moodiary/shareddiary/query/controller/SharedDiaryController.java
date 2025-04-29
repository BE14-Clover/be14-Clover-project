package com.clover.moodiary.shareddiary.query.controller;

import com.clover.moodiary.shareddiary.query.dto.SharedDiaryResponse;
import com.clover.moodiary.shareddiary.query.service.SharedDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shareddiary")
@RequiredArgsConstructor
public class SharedDiaryController {

    private final SharedDiaryService sharedDiaryService;

    @GetMapping
    public List<SharedDiaryResponse> findDiaryByRoomId(@RequestParam(value="roomId") Integer roomId) {
        return sharedDiaryService.findDiaryByRoomId(roomId);
    }
}
