package com.clover.moodiary.myDiary.command.application.service;

import com.clover.moodiary.myDiary.command.application.dto.EmotionAnalysisDTO;
import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MyDiaryCommandService {

    void registDiary(MyDiaryCommandDTO myDiaryCommandDTO, MultipartFile image);

    void saveMoodlog(MoodlogDTO dto);

    void updateDiary(MyDiaryCommandDTO myDiaryCommandDTO);

    void deleteDiary(Integer diaryId);

    void saveEmotionAnalysis(EmotionAnalysisDTO dto);
}

