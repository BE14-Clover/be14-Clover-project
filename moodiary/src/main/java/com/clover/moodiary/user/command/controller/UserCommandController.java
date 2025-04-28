package com.clover.moodiary.user.command.controller;

import com.clover.moodiary.user.command.dto.*;
import com.clover.moodiary.user.command.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/command")
@RequiredArgsConstructor
public class UserCommandController {
	private final UserCommandService svc;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequest dto) {
		svc.register(dto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dto) {
		return ResponseEntity.ok(svc.login(dto));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
		svc.logout(token.replace("Bearer ", ""));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/delete/{userId}")
	public ResponseEntity<Void> delete(@PathVariable int userId) {
		svc.deleteAccount(userId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/update")
	public ResponseEntity<Void> update(@RequestBody UpdateUserRequest dto) {
		svc.updateUser(dto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/request-password-reset")
	public ResponseEntity<Void> requestReset(@RequestBody PasswordResetRequest dto) {
		svc.requestPasswordReset(dto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/reset-password")
	public ResponseEntity<Void> reset(@RequestBody PasswordReset dto) {
		svc.resetPassword(dto);
		return ResponseEntity.ok().build();
	}
}
