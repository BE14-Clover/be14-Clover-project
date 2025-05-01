package com.clover.moodiary.myDiary.command.domain.repository;

import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryTagEntity;
import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryTagId;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDiaryTagRepository extends JpaRepository<MyDiaryTagEntity, MyDiaryTagId> {

    @Transactional
    @Modifying
    @Query("DELETE FROM MyDiaryTagEntity t WHERE t.myDiary.id = :diaryId")
    void deleteByDiaryId(@Param("diaryId") Integer diaryId);

}
