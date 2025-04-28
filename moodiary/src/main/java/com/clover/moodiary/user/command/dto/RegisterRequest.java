// com.clover.moodiary.user.command.dto/RegisterRequest.java
package com.clover.moodiary.user.command.dto;
import lombok.*;

@Getter @Setter
public class RegisterRequest {
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
}
