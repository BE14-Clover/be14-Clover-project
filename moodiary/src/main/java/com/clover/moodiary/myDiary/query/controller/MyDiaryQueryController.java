package com.clover.moodiary.myDiary.query.controller;

import com.clover.moodiary.myDiary.query.dto.MonthlyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.query.dto.WeeklyDiaryDTO;
import com.clover.moodiary.myDiary.query.service.MyDiaryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/mydiary")
@RequiredArgsConstructor
public class MyDiaryQueryController {

    private final MyDiaryQueryService myDiaryQueryService;

    @GetMapping("/monthly")
    public List<MonthlyDiaryDTO> getMonthlyDiaries(@RequestParam String targetMonth,
                                               @RequestParam int userId) {
        log.info("요청 받은 월간 일기 조회의 userId: {}, targetMonth: {}", userId, targetMonth);
        return myDiaryQueryService.getDiaryForMonth(targetMonth, userId);
    }

    @GetMapping("/moodlog")
    public MoodlogDTO getMoodlog(@RequestParam String targetMonth,
                                 @RequestParam int userId) {
        log.info("요청 받은 Moodlog 조회의 userId: {}, targetMonth: {}", userId, targetMonth);
        return myDiaryQueryService.getMoodlog(targetMonth, userId);
    }

    @GetMapping("/weekly")
    public List<WeeklyDiaryDTO> getWeeklyDiaries(@RequestParam String startDate,
                                                 @RequestParam String endDate,
                                                 @RequestParam int userId) {
        log.info("주간 일기 조회 요청 - userId: {}, startDate: {}, endDate: {}", userId, startDate, endDate);
        return myDiaryQueryService.getDiaryForWeek(startDate, endDate, userId);
    }
}