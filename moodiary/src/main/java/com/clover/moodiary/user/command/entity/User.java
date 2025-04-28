// com.clover.moodiary.user.command.entity/User.java
package com.clover.moodiary.user.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;        // BCrypt 인코딩

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "is_deleted", nullable = false)
	private Boolean deleted = false;

	// 비밀번호 재설정 토큰 매핑 (Optional)
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private PasswordResetToken resetToken;
}
