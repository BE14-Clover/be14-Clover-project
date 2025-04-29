package com.clover.moodiary.myDiary.command.domain.repository;

import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryTagEntity;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDiaryTagRepository extends JpaRepository<MyDiaryTagEntity, MyDiaryTagId> {
}