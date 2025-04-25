package com.clover.moodiary.action;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.clover.moodiary.action.command.application.service.CommandActionService;
import com.clover.moodiary.action.command.domain.repository.UserPreferencesRepository;

@SpringBootTest
public class InsertInitialUserPreferencesTest {
	
	@Autowired
	private CommandActionService commandActionService;
	@Autowired
	private UserPreferencesRepository userPreferencesRepository;
	
	@ParameterizedTest
	@DisplayName("사용자 선호도 초기 값 세팅 테스트(초기값 50)")
	@ValueSource(ints = {1})
	public void insertInitialUserPreferencesTest(int userId) {
		commandActionService.insertInitialUserPreferences(userId);
		Assertions.assertEquals(50, Objects.requireNonNull(userPreferencesRepository.findById(userId).orElse(null)).getWeight());
	}
}
