package com.clover.moodiary.shareddiary.command.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "shared_diary")
@Getter
@Setter
@ToString
public class SharedDiary {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_Deleted")
    private String isDeleted;

    @Column(name = "fixed_State")
    private String fixedState;

    @Column(name = "style_layer")
    private String styleLayer;

    @Column(name = "shared_diary_room_id")
    private Integer sharedDiaryRoomId;

    @Column(name = "user_id")
    private Integer userId;
}
