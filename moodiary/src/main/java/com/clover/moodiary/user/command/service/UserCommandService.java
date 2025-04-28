// com.clover.moodiary.user.command.service/UserCommandService.java
package com.clover.moodiary.user.command.service;

import com.clover.moodiary.user.command.dto.*;

public interface UserCommandService {
	void register(RegisterRequest dto);

	void deleteAccount(int userId);

	LoginResponse login(LoginRequest dto);

	void logout(String authToken);

	void requestPasswordReset(PasswordResetRequest dto);

	void resetPassword(PasswordReset dto);
}
