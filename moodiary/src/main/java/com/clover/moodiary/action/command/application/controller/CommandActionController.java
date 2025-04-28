package com.clover.moodiary.action.command.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clover.moodiary.action.command.application.service.CommandActionService;
import com.clover.moodiary.action.query.service.ActionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/action")
@Slf4j
@RequiredArgsConstructor
public class CommandActionController {
	private final CommandActionService commandActionService;
	private final ActionService actionService;
	
	/* 목차. 회원 가중치 전체 초기화 */
	/* 설명. 회원가입 시 혹은 가중치 초기화 버튼 클릭 시 */
	/* TODO. 일단 RequestParam으로 userID를 받아온다고 가정 */
	@PostMapping("/weight/init")
	public ResponseEntity<String> initUserPreference(@RequestParam(value = "userId") int userId) {
		try {
			commandActionService.insertInitialUserPreferences(userId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok("User Preferences Initialization Success");
	}
	
	/* 목차. 회원의 가중치 증가 */
	@PostMapping("/{actionId}/plus")
	public ResponseEntity<String> plusUserPreferences(@PathVariable(value = "actionId") int actionId, @RequestParam(value = "userId") int userId) {
		commandActionService.plusUserPreferences(userId, actionId);
		String actionName = actionService.getRecommendedActionById(actionId).getAction();
		return ResponseEntity.ok(actionName + " weight plussed");
	}
	
	/* 목차. 회원의 가중치 감소 */
	
	/* 목차. 추천 행동 필터링 */
	/* 설명. 추천 행동 목록 필터로 체크된 목록 받으면(적용) 해당 목록에 해당하는 가중치 0으로 설정 */
	
}
