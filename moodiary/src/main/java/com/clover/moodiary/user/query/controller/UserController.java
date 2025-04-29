package com.clover.moodiary.user.query.controller;

import com.clover.moodiary.user.query.dto.RequestEmailDTO;
import com.clover.moodiary.user.query.dto.UserEmailDTO;
import com.clover.moodiary.user.query.dto.UserDTO;
import com.clover.moodiary.user.query.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/query")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/email")
	public ResponseEntity<UserEmailDTO> findEmail(@RequestBody RequestEmailDTO request) {
		return ResponseEntity.ok(userService.findEmail(request));
	}

	@GetMapping("/info/{userId}")
	public ResponseEntity<UserDTO> findInfo(@PathVariable int userId) {
		return ResponseEntity.ok(userService.findInfo(userId));
	}
}
