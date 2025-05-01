package com.clover.moodiary.myDiary.command.domain.repository;

import com.clover.moodiary.myDiary.command.domain.aggregate.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    Optional<TagEntity> findByTagName(String tagName);
}

