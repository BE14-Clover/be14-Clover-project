// com.clover.moodiary.user.command.entity/PasswordResetToken.java
package com.clover.moodiary.user.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PasswordResetToken {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String token;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
