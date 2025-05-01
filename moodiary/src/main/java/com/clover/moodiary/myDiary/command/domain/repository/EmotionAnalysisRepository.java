package com.clover.moodiary.myDiary.command.domain.repository;

import com.clover.moodiary.myDiary.command.domain.aggregate.entity.EmotionAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionAnalysisRepository extends JpaRepository<EmotionAnalysisEntity, Integer> {
}
