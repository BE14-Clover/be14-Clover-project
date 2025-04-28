package com.clover.moodiary.user.command.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clover.moodiary.user.command.service.UserCommandService;

import lombok.extern.slf4j.Slf4j;

@RestController("UserCommandController")
@RequestMapping("/user")
@Slf4j
public class UserCommandController {

	private final UserCommandService userCommandService;

	@Autowired
	public UserCommandController(UserCommandService userCommandService) {
		this.userCommandService = userCommandService;
	}

}
