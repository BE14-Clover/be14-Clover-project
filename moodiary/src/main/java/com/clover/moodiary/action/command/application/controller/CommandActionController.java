package com.clover.moodiary.action.command.application.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@PostMapping("/weight/init")
	public ResponseEntity<String> initUserPreference() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer userId = (Integer) auth.getPrincipal();
		try {
			commandActionService.insertInitialUserPreferences(userId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok("User Preferences Initialization Success");
	}
	
	/* 목차. 회원의 가중치 변경 */
	@PostMapping("/{actionId}/change")
	public ResponseEntity<String> plusUserPreferences(@PathVariable(value = "actionId") int actionId, @RequestBody Map<String, Integer> changeValue) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer userId = (Integer) auth.getPrincipal();
		commandActionService.changeUserPreferences(userId, actionId, changeValue.get("changeValue"));
		String actionName = actionService.getRecommendedActionById(actionId).getAction();
		return ResponseEntity.ok(actionName + " weight changed by" + changeValue.get("changeValue"));
	}
	
	/* 목차. 추천 행동 태그 필터링 */
	/* 설명. 추천 행동 태그 목록 필터로 체크된 목록 받으면(적용) 해당 목록에 해당하는 가중치 0으로 설정 */
	@PostMapping("/exclude")
	public ResponseEntity<String> excludeUserPreferences(@RequestBody List<Integer> excludingActionTagList) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer userId = (Integer) auth.getPrincipal();
		commandActionService.excludeActionTagList(userId, excludingActionTagList);
		return ResponseEntity.ok("Following Action Tags Are Excluded: " + excludingActionTagList.toString());
	}
}
