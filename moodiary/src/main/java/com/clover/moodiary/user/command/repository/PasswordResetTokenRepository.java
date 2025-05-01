// com.clover.moodiary.user.command.repository/PasswordResetTokenRepository.java
package com.clover.moodiary.user.command.repository;

import com.clover.moodiary.user.command.entity.PasswordResetToken;
import com.clover.moodiary.user.command.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import jakarta.transaction.Transactional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	Optional<PasswordResetToken> findByToken(String token);

	@Modifying
	@Transactional
	@Query("DELETE FROM PasswordResetToken t WHERE t.user = :user")
	void deleteAllByUser(@Param("user") User user);
}
