package com.clover.moodiary.user.command.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "PHONE_NUMBER", nullable = false, unique = true)
	private String phone_number;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "is_deleted", nullable = false)
	private String is_deleted;

	@Column(name = "answer", nullable = false)
	private String answer;

	@Column(name = "register_questions_id", nullable = false)
	private int register_questions_id;
}
