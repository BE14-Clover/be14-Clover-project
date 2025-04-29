// com/clover/moodiary/user/command/entity/User.java
package com.clover.moodiary.user.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "register_questions_id", nullable = false)
	private RegisterQuestion registerQuestion;

	@Column(nullable = false)
	private String answer;

	@Builder.Default
	@Column(
		name = "is_deleted",
		nullable = false,
		length = 4,
		columnDefinition = "VARCHAR(4) NOT NULL DEFAULT 'N'"
	)
	private Boolean deleted = false;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private PasswordResetToken resetToken;
}
