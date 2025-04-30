package com.clover.moodiary.myDiary.command.domain.repository;

import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MoodlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MoodlogRepository extends JpaRepository<MoodlogEntity, Integer> {
    boolean existsByUserIdAndMonth(Integer userId, LocalDate month);
}