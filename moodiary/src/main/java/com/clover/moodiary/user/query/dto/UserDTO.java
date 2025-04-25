package com.clover.moodiary.user.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private int id;
	private String name;
	private String phoneNumber;
	private String email;
	private String password;
	private String isDeleted;
	private String answer;
	private String registerQuestionsId;
}
