package com.clover.moodiary.user.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "register_questions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterQuestion {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "register_questions_content" , nullable = false)
	private String question;
}
