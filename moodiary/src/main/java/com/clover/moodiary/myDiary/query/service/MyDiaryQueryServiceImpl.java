package com.clover.moodiary.myDiary.query.service;

import com.clover.moodiary.myDiary.query.dto.MonthlyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.query.dto.MyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.WeeklyDiaryDTO;
import com.clover.moodiary.myDiary.query.mapper.MyDiaryMapper;
import com.clover.moodiary.myDiary.query.service.MyDiaryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyDiaryQueryServiceImpl implements MyDiaryQueryService {

    private final MyDiaryMapper myDiaryMapper;

    @Override
    public List<MonthlyDiaryDTO> getDiaryForMonth(String targetMonth, int userId) {
        return myDiaryMapper.selectDiaryForMonth(targetMonth, userId);
    }

    @Override
    public List<WeeklyDiaryDTO> getDiaryForWeek(String startDate, String endDate, int userId) {
        return myDiaryMapper.selectDiaryForWeek(startDate, endDate, userId);

    }

    @Override
    public MoodlogDTO getMoodlog(String targetMonth, int userId) {
        return myDiaryMapper.selectMoodlog(targetMonth, userId);
    }

    @Override
    public MyDiaryDTO getDiaryByDateKST(LocalDate date, int userId) {
        // KST 기준 하루의 시작과 끝을 LocalDateTime으로 계산
        LocalDateTime startOfDay = date.atStartOfDay();              // 00:00
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();    // 다음날 00:00

        return myDiaryMapper.selectDiaryByDateRange(startOfDay, endOfDay, userId);
    }
}
