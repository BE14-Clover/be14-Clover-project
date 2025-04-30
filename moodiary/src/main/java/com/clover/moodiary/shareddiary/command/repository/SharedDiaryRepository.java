package com.clover.moodiary.shareddiary.command.repository;

import com.clover.moodiary.shareddiary.command.entity.SharedDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedDiaryRepository extends JpaRepository<SharedDiary, Integer> {
}
