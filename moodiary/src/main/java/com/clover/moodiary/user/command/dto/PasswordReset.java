package com.clover.moodiary.user.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordReset {
	private String token;
	private String newPassword;
}