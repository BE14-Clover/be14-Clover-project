package com.clover.moodiary.action.command.application.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clover.moodiary.action.command.application.service.CommandActionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/action")
@Slf4j
@RequiredArgsConstructor
public class CommandActionController {
	private final CommandActionService commandActionService;
	
	/* 목차. 회원 가중치 초기화 */
	/* 설명. 회원가입 시 혹은 가중치 초기화 버튼 클릭 시  */
	/* TODO. 일단 RequestBody로 userID를 받아온다고 가정 */
	@PostMapping("/weight/init")
	public ResponseEntity<String> initUserPreference(@RequestBody int userId) {
		try {
			commandActionService.insertInitialUserPreferences(userId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.created(URI.create("/")) // 메인 화면으로 이동
							 .build();
	}
	
	/* 목차. 회원의 가중치 증가 */
	
	/* 목차. 회원의 가중치 감소 */
	
}
