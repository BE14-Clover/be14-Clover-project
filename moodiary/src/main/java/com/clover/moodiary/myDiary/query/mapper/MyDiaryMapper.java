package com.clover.moodiary.myDiary.query.mapper;

import com.clover.moodiary.myDiary.query.dto.MonthlyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.query.dto.WeeklyDiaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyDiaryMapper {
    List<MonthlyDiaryDTO> selectDiaryForMonth(@Param("targetMonth") String targetMonth,
                                              @Param("userId") int userId);

    List<WeeklyDiaryDTO> selectDiaryForWeek(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            @Param("userId") int userId);

    MoodlogDTO selectMoodlog(@Param("targetMonth") String targetMonth,
                             @Param("userId") int userId);
}