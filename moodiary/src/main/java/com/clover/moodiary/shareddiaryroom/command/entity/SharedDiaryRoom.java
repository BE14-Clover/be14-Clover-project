package com.clover.moodiary.shareddiaryroom.command.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="shared_diary_room" )
@Getter
@NoArgsConstructor
public class SharedDiaryRoom {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_id1")
    private Integer userId1;

    @Column(name="user_id2")
    private Integer userId2;

    public SharedDiaryRoom(Integer userId1) {
        this.userId1 = userId1;
    }
}
