// com.clover.moodiary.user.command.repository/PasswordResetTokenRepository.java
package com.clover.moodiary.user.command.repository;

import com.clover.moodiary.user.command.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	Optional<PasswordResetToken> findByToken(String token);
}
