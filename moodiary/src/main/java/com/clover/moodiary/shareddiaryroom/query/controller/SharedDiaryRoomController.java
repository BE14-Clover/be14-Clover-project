package com.clover.moodiary.shareddiaryroom.query.controller;

import com.clover.moodiary.shareddiaryroom.query.dto.SharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.query.service.SharedDiaryRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shareddiaryroom")
@RequiredArgsConstructor
public class SharedDiaryRoomController {

    private final SharedDiaryRoomService sharedDiaryRoomService;

    @GetMapping
    public List<SharedDiaryRoomResponse> findRoomByUserId(@RequestParam(value="userId") Integer userId) {
        return sharedDiaryRoomService.findRoomByUserId(userId);
    }


}
