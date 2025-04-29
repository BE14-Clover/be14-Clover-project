package com.clover.moodiary.shareddiaryroom.query.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "shared_diary_room")
@Getter
@Setter
@ToString
public class SharedDiaryRoom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id1", nullable = false)
    private Integer userId1;

    @Column(name = "user_id2", nullable = false)
    private Integer userId2;
}
