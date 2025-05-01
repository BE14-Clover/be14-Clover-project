package com.clover.moodiary.shareddiary.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SharedDiaryResponse {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String isDeleted;
    private String fixedState;
    private Integer userId;
    private String userName;
}
