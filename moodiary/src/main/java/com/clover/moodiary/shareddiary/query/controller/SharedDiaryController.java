package com.clover.moodiary.shareddiary.query.controller;

import com.clover.moodiary.shareddiary.query.dto.SharedDiaryDetailResponse;
import com.clover.moodiary.shareddiary.query.dto.SharedDiaryResponse;
import com.clover.moodiary.shareddiary.query.service.SharedDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{diaryId}")
    public SharedDiaryDetailResponse findDiaryById(@PathVariable("diaryId") Integer diaryId) {
        return sharedDiaryService.findDiaryById(diaryId);
    }
}
