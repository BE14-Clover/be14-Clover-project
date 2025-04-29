package com.clover.moodiary.myDiary.command.domain.repository;

import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MyDiaryRepository extends JpaRepository<MyDiaryEntity, Integer> {

    @Query("SELECT d FROM MyDiaryEntity d WHERE DATE(d.createdAt) = :createdDate AND d.userId = :userId")
    Optional<MyDiaryEntity> findByCreatedDateAndUserId(@Param("createdDate") LocalDate createdDate, @Param("userId") Integer userId);
}
