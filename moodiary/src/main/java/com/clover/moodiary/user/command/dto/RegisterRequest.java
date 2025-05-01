package com.clover.moodiary.user.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
	private String name;
	private String email;
	private String password;
	private String phoneNumber;

	private Long registerQuestionsId;
	private String answer;
}
