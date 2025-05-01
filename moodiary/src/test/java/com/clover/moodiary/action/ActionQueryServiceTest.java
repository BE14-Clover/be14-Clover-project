package com.clover.moodiary.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.clover.moodiary.action.command.application.service.CommandActionService;
import com.clover.moodiary.action.query.service.ActionService;

@SpringBootTest
public class ActionQueryServiceTest {
	
	@Autowired
	private ActionService actionService;
	
	@Autowired
	private CommandActionService commandActionService;
	
	@Test
	public void getAllActionTagIdsTest() {
		Assertions.assertFalse(actionService.getAllActionTagIds().isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1})
	public void getRecommendedActionByIdTest(int actionId) {
		Assertions.assertNotNull(actionService.getRecommendedActionById(actionId));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1})
	public void getActionTagByActionIdTest(int actionId) {
		Assertions.assertNotNull(actionService.getActionTagByActionId(actionId));
	}
	
	@ParameterizedTest
	@CsvSource({
		"1, 1, 50"
	})
	public void getUserPreferencesByActionTagIdTest(int userId, int actionTagId, int expectedValue) {
		commandActionService.insertInitialUserPreferences(userId);
		Assertions.assertEquals(
			expectedValue,
			actionService.getUserPreferencesByActionTagId(userId, actionTagId).getWeight()
		);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {11})
	public void getParentActionTagByActionTagTest(int actionTagId) {
		Assertions.assertNotNull(actionService.getParentActionTagByActionTag(actionTagId));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1})
	public void getThreeActionsTest(int userId) {
		Assertions.assertNotNull(actionService.getThreeActions(userId));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"감"})
	public void searchActionTagListByActionTagName(String keyword) {
		Assertions.assertNotNull(actionService.searchActionTagListByActionTagName(keyword));
	}
}