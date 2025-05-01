package com.clover.moodiary.myDiary.query.controller;

import com.clover.moodiary.myDiary.query.dto.MonthlyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.query.dto.MyDiaryDTO;
import com.clover.moodiary.myDiary.query.dto.WeeklyDiaryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MyDiaryQueryControllerTest {

    @Autowired
    private MyDiaryQueryController myDiaryQueryController;

    private void setAuthentication(Integer userId) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userId, null, null)
        );
    }

    @Test
    @DisplayName("월간 일기 조회 - 통합 테스트")
    void testGetMonthlyDiaries() {
        setAuthentication(1);  // 하드코딩 userId = 1
        List<MonthlyDiaryDTO> result = myDiaryQueryController.getMonthlyDiaries("2025-05");

        assertNotNull(result);
        assertTrue(result instanceof List);
    }

    @Test
    @DisplayName("주간 일기 조회 - 통합 테스트")
    void testGetWeeklyDiaries() {
        setAuthentication(1);
        List<WeeklyDiaryDTO> result = myDiaryQueryController.getWeeklyDiaries("2025-04-28", "2025-05-05");

        assertNotNull(result);
        assertTrue(result instanceof List);
    }


    @Test
    @DisplayName("일기 조회 (날짜 요청 파라미터) - 통합 테스트")
    void testGetDiaryByDateRequestParam() {
        setAuthentication(1);
        MyDiaryDTO result = myDiaryQueryController.getDiaryByDate(LocalDate.of(2025, 5, 2));

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    @Test
    @DisplayName("일기 조회 (PathVariable) - 통합 테스트")
    void testGetDiaryByDatePathVariable() {
        setAuthentication(1);
        MyDiaryDTO result = myDiaryQueryController.getDiaryByDate("2025-05-02");

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }
}