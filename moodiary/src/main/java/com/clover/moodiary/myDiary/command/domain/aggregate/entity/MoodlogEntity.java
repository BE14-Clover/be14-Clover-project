package com.clover.moodiary.myDiary.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "moodlog", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "month"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MoodlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate month;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
}
