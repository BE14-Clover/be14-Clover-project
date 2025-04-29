package com.clover.moodiary.myDiary.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emotion_analysis")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EmotionAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "positive_score", nullable = false)
    private Integer positiveScore;

    @Column(name = "neutral_score", nullable = false)
    private Integer neutralScore;

    @Column(name = "negative_score", nullable = false)
    private Integer negativeScore;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Column(name = "emotion_summary1", nullable = false)
    private String emotionSummary1;

    @Column(name = "emotion_summary2", nullable = false)
    private String emotionSummary2;

    @Column(name = "emotion_summary3", nullable = false)
    private String emotionSummary3;

    @Column(name = "diary_summary", nullable = false)
    private String diarySummary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_diary_id", nullable = false)
    private MyDiaryEntity myDiaryEntity;
}