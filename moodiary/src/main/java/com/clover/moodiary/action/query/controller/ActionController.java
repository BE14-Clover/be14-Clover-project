package com.clover.moodiary.action.query.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clover.moodiary.action.command.application.dto.ActionTagDTO;
import com.clover.moodiary.action.command.application.dto.RecommendedActionDTO;
import com.clover.moodiary.action.query.service.ActionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/action")
@Slf4j
@RequiredArgsConstructor
public class ActionController {
	
	private final ActionService actionService;
	
	/* 설명. 서버 상태 체크용 */
	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {
		return ResponseEntity.ok("OK");
	}
	
	/* 목차. 회원의 가중치를 바탕으로 랜덤한 행동 3개 뽑아주기 */
	/* 설명. 일단 최근 추천한 내역은 고려하지 않고 가중치로만 뽑았습니다. */
	@GetMapping("/recommend")
	public ResponseEntity<List<RecommendedActionDTO>> recommendThreeActions(@RequestParam(value = "userId") int userId) {
		List<RecommendedActionDTO> recommendList;
		try {
			recommendList = actionService.getThreeActions(userId);
		} catch (RuntimeException e) {
			RecommendedActionDTO errorMessage = new RecommendedActionDTO(-1, e.getMessage());
			List<RecommendedActionDTO> errorMessageList = new ArrayList<>();
			errorMessageList.add(errorMessage);
			return ResponseEntity.status(404).body(errorMessageList);
		}
		return ResponseEntity.ok(recommendList);
	}
	
	/* 목차. 추천 행동 태그 이름으로 검색 */
	/* 설명. */
	@GetMapping("/search")
	public ResponseEntity<List<ActionTagDTO>> searchActionTagIdListByActionTagName(@RequestParam(value = "keyword") String keyword) {
		return ResponseEntity.ok(actionService.searchActionTagListByActionTagName(keyword));
	}
}
