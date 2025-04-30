package com.clover.moodiary.shareddiaryroom.query.controller;

import com.clover.moodiary.shareddiaryroom.query.dto.SharedDiaryRoomResponse;
import com.clover.moodiary.shareddiaryroom.query.service.SharedDiaryRoomQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shareddiaryroom")
@RequiredArgsConstructor
public class SharedDiaryRoomQueryController {

    private final SharedDiaryRoomQueryService sharedDiaryRoomQueryService;

    @GetMapping
    public List<SharedDiaryRoomResponse> findRoomByUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer)auth.getPrincipal();

        return sharedDiaryRoomQueryService.findRoomByUserId(userId);
    }


}
