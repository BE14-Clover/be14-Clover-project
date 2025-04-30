package com.clover.moodiary.myDiary.command.application.service;

import com.clover.moodiary.myDiary.command.application.dto.MyDiaryCommandDTO;
import com.clover.moodiary.myDiary.command.application.dto.MoodlogDTO;

public interface MyDiaryCommandService {

    void registDiary(MyDiaryCommandDTO myDiaryCommandDTO);

    void saveMoodlog(MoodlogDTO dto);
}
