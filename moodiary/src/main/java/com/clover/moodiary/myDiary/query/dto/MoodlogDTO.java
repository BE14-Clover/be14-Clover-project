package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MoodlogDTO {
    private Integer id;
    private String content;
    private java.util.Date month;

    private Integer user_id;
}
