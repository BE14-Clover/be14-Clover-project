package com.clover.moodiary.user.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clover.moodiary.user.query.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController("UserController")
@RequestMapping("/user")
@Slf4j
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;

	}

	@GetMapping("/email")
	public ResponseEntity<UserEmailDTO> getEmail(@RequestBody UserEmailDTO request) {
		return ResponseEntity.ok(response);
	}
}
