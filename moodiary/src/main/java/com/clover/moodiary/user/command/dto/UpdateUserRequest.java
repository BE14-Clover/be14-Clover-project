package com.clover.moodiary.user.command.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 개인 정보 수정용 DTO
 * 이메일·이름·전화번호·새 비밀번호 중 원하는 필드만 전달 가능
 */
@Getter
@Setter
public class UpdateUserRequest {
	private Integer id;
	private String name;
	private String email;
	private String phoneNumber;
	private String newPassword;
}
