package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyDiaryDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String isDeleted;
    private String isConfirmed;
    private String styleLayer;
    private Integer userId;

    private List<String> tags;
    private MyDiaryEmotionDTO myDiaryEmotion;
}