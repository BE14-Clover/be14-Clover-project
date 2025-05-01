package com.clover.moodiary.myDiary.query.controller;

import com.clover.moodiary.myDiary.query.dto.MonthlyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.query.dto.MyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.WeeklyDiaryDTO;
import com.clover.moodiary.myDiary.query.service.MyDiaryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    @GetMapping("/daily")
    public MyDiaryDTO getDiaryByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     @RequestParam("userId") int userId) {
        log.info("🇰🇷 한국시간 일기 조회 요청 - userId: {}, date: {}", userId, date);
        return myDiaryQueryService.getDiaryByDateKST(date, userId);
    }

    @GetMapping("/daily/{date}")
    public MyDiaryDTO getDiaryByDate(@PathVariable("date") String date,
                                     @RequestParam("userId") int userId) {
        log.info("일기 조회 요청 - userId: {}, date: {}", userId, date);
        try {
            LocalDate diaryDate = LocalDate.parse(date);
            return myDiaryQueryService.getDiaryByDateKST(diaryDate, userId);
        } catch (DateTimeParseException e) {
            log.error("날짜 파싱 실패 - 잘못된 형식: {}", date);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "날짜 형식이 잘못되었습니다. YYYY-MM-DD 형식이어야 합니다.");
        } catch (Exception e) {
            log.error("일기 조회 중 서버 에러", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
        }
    }
}
