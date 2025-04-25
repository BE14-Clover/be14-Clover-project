package com.clover.moodiary.user.query.service;

import com.clover.moodiary.user.query.dto.RequestEmailDTO;
import com.clover.moodiary.user.query.dto.RequestPasswordDTO;
import com.clover.moodiary.user.query.dto.UserDTO;
import com.clover.moodiary.user.query.dto.UserEmailDTO;
import com.clover.moodiary.user.query.dto.UserPasswordDTO;

public interface UserService {
	UserEmailDTO findEmail(RequestEmailDTO request);

	UserPasswordDTO findPassword(RequestPasswordDTO request);

	UserDTO findInfo(int userId);
}
