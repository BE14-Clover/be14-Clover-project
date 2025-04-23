package com.clover.moodiary.user.query.service;

import com.clover.moodiary.user.query.dto.RequestEmailDTO;
import com.clover.moodiary.user.query.dto.UserEmailDTO;
import com.clover.moodiary.user.query.dto.UserPasswordDTO;

public interface UserService {
	static UserEmailDTO findEmail(RequestEmailDTO request) {}

	static UserPasswordDTO findPassowrd(RequestPasswordDTO request) {}
}
