package com.clover.moodiary.user.query.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPasswordDTO {
	private String name;
	private String phoneNumber;
	private String email;
}
