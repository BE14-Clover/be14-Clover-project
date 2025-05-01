package com.clover.moodiary.user.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
	private Integer id;
	private String email;
	private String name;
}
