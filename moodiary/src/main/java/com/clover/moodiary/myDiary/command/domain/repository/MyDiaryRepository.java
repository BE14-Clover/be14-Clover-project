package com.clover.moodiary.myDiary.command.domain.repository;

import com.clover.moodiary.myDiary.command.domain.aggregate.entity.MyDiaryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MyDiaryRepository extends JpaRepository<MyDiaryEntity, Integer> {

    @Query("SELECT d FROM MyDiaryEntity d WHERE DATE(d.createdAt) = :createdDate " +
            "AND d.userId = :userId AND d.isDeleted = 'N'")
    Optional<MyDiaryEntity> findByCreatedAtAndUserId(@Param("createdDate") LocalDate createdAt,
                                                       @Param("userId") Integer userId);

    boolean existsByCreatedAtBetweenAndUserIdAndIsDeleted(LocalDateTime start,
                                                          LocalDateTime end,
                                                          Integer userId,
                                                          String isDeleted);

    boolean existsByCreatedAtAndUserIdAndIsDeleted(LocalDateTime createdAt, Integer userId, String isDeleted);

    Optional<MyDiaryEntity> findByIdAndIsDeleted(Integer id, String isDeleted);
}

