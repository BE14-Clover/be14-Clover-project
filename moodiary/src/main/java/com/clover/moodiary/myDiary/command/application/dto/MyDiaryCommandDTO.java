package com.clover.moodiary.myDiary.command.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyDiaryCommandDTO {
    private Integer id;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private String isDeleted;
    private String isConfirmed;
    private String styleLayer;
    private Integer userId;
    private List<String> tags;
}