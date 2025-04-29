package com.clover.moodiary.myDiary.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "my_diary")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyDiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private String isDeleted;

    @Column(name = "is_confirmed", nullable = false)
    private String isConfirmed;

    @Lob
    @Column(name = "style_layer")
    private String styleLayer;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
}