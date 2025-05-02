package com.clover.moodiary.myDiary.command.application.controller;

import com.clover.moodiary.myDiary.command.application.dto.EmotionAnalysisDTO;
import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MyDiaryCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // userId mock
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(1, null, null)
        );
    }

    @Transactional
    @Test
    @DisplayName("/regist 일기 등록 API 테스트")
    void registDiary() throws Exception {
        MyDiaryCommandDTO dto = new MyDiaryCommandDTO();
        dto.setTitle("테스트 제목");
        dto.setContent("테스트 내용");
        dto.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0));
        dto.setIsConfirmed("N");
        dto.setUserId(3);
        dto.setTags(List.of("태그1", "태그2"));

        MockMultipartFile dtoPart = new MockMultipartFile(
                "dto", "", "application/json", objectMapper.writeValueAsBytes(dto));
        MockMultipartFile imagePart = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "dummy".getBytes());

        mockMvc.perform(multipart("/mydiary/regist")
                        .file(dtoPart)
                        .file(imagePart))
                .andExpect(status().isCreated())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("일기 등록 완료"));
    }

    @Transactional
    @Test
    @DisplayName("/update 일기 수정 API 테스트")
    void updateDiary() throws Exception {
        MyDiaryCommandDTO dto = new MyDiaryCommandDTO();
        dto.setId(2);
        dto.setTitle("수정 제목");
        dto.setContent("수정 내용");
        dto.setUserId(1);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setIsConfirmed("N");

        mockMvc.perform(put("/mydiary/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse()
                        .getContentAsString()).contains("일기 수정 완료"));
    }

    @Transactional
    @Test
    @DisplayName("/upload 이미지 업로드 API 테스트")
    void uploadImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "이미지내용".getBytes());

        mockMvc.perform(multipart("/mydiary/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists());
    }

    @Transactional
    @Test
    @DisplayName("/delete 일기 삭제 API 테스트")
    void deleteDiary() throws Exception {
        mockMvc.perform(delete("/mydiary/{diaryId}", 2))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("일기 삭제"));
    }

    @Transactional
    @Test
    @DisplayName("/registEmotion 감정 분석 등록 API 테스트")
    void registEmotion() throws Exception {
        EmotionAnalysisDTO dto = new EmotionAnalysisDTO();
        dto.setMyDiaryId(1);
        dto.setPositiveScore(80);
        dto.setNeutralScore(10);
        dto.setNegativeScore(10);
        dto.setTotalScore(100);
        dto.setEmotionSummary1("요약1");
        dto.setEmotionSummary2("요약2");
        dto.setEmotionSummary3("요약3");
        dto.setMyDiarySummary("전체요약");

        mockMvc.perform(post("/mydiary/registEmotion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Transactional
    @Test
    @DisplayName("/moodlog 기분 로그 등록 API 테스트")
    void registMoodlog() throws Exception {
        MoodlogDTO dto = new MoodlogDTO();
        dto.setUserId(1);
        dto.setContent("오늘 기분 좋음");
        dto.setTargetMonth("2025-05-01");

        mockMvc.perform(post("/mydiary/moodlog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString()).contains("Moodlog 등록 완료"));
    }
}