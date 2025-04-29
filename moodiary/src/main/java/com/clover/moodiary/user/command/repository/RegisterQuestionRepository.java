package com.clover.moodiary.user.command.repository;

import com.clover.moodiary.user.command.entity.RegisterQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterQuestionRepository extends JpaRepository<RegisterQuestion, Long> {
}
