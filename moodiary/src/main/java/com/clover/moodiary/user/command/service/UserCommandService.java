package com.clover.moodiary.user.command.service;

import com.clover.moodiary.user.command.dto.*;

public interface UserCommandService {
	void register(RegisterRequest dto);

	LoginResponse login(LoginRequest dto);

	void logout(String authToken);

	void deleteAccount(int userId);

	void updateUser(UpdateUserRequest dto);               // 추가

	void requestPasswordReset(PasswordResetRequest dto);

	void resetPassword(PasswordReset dto);
}
