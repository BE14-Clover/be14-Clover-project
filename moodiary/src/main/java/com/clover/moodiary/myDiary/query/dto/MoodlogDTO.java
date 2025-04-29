package com.clover.moodiary.myDiary.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MoodlogDTO {
    private int id;
    private String content;
    private java.util.Date month;

    private int user_id;
}
