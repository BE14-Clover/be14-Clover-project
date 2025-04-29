package com.clover.moodiary.myDiary.query.service;

import com.clover.moodiary.myDiary.query.dto.MonthlyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.query.dto.WeeklyDiaryDTO;
import com.clover.moodiary.myDiary.query.mapper.MyDiaryMapper;
import com.clover.moodiary.myDiary.query.service.MyDiaryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

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
}