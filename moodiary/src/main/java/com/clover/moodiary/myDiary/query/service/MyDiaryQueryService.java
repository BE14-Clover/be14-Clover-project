package com.clover.moodiary.myDiary.query.service;

import com.clover.moodiary.myDiary.query.dto.MonthlyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.query.dto.MyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.WeeklyDiaryDTO;

import java.time.LocalDate;
import java.util.List;

public interface MyDiaryQueryService {

    // 월간 나의 일기 조회
    List<MonthlyDiaryDTO> getDiaryForMonth(String targetMonth, int userId);

    // 주간 나의 일기 조회
    List<WeeklyDiaryDTO> getDiaryForWeek(String startDate, String endDate, int userId);

    // Moodlog 조회
    MoodlogDTO getMoodlog(String targetMonth, int userId);

    // 일기 조회
    MyDiaryDTO getDiaryByDateKST(LocalDate date, int userId);
}
