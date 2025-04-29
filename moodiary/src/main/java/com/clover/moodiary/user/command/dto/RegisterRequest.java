package com.clover.moodiary.user.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	private String name;
	private String email;
	private String password;
	private String phoneNumber;

	private Long registerQuestionsId;
	private String answer;
}
