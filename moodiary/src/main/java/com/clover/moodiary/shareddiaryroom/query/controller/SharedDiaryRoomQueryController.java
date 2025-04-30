package com.clover.moodiary.shareddiaryroom.query.controller;

import com.clover.moodiary.shareddiaryroom.query.dto.SharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.query.service.SharedDiaryRoomQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shareddiaryroom")
@RequiredArgsConstructor
public class SharedDiaryRoomQueryController {

    private final SharedDiaryRoomQueryService sharedDiaryRoomService;

    @GetMapping
    public List<SharedDiaryRoomResponse> findRoomByUserId(@RequestParam(value="userId") Integer userId) {
        return sharedDiaryRoomService.findRoomByUserId(userId);
    }


}
