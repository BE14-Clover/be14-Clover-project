package com.clover.moodiary.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.clover.moodiary.action.command.application.service.CommandActionService;
import com.clover.moodiary.action.command.domain.aggregate.entity.UserPreferencesPK;
import com.clover.moodiary.action.command.domain.repository.UserPreferencesRepository;

@SpringBootTest
public class ActionCommandTest {
	
	@Autowired
	private CommandActionService commandActionService;
	@Autowired
	private UserPreferencesRepository userPreferencesRepository;
	
	@BeforeEach
	void setUp() {
		commandActionService.insertInitialUserPreferences(1);
		commandActionService.insertInitialUserPreferences(2);
		commandActionService.insertInitialUserPreferences(3);
		commandActionService.insertInitialUserPreferences(4);
		commandActionService.insertInitialUserPreferences(5);
	}
	
	@ParameterizedTest
	@DisplayName("사용자 선호도 초기 값 세팅 테스트(초기값 50)")
	@CsvSource({
		"1, 1, 50",
		"1, 2, 50",
		"2, 1, 50"
	})
	public void insertInitialUserPreferencesTest(int userId, int actionId, int expectedValue) {
		commandActionService.insertInitialUserPreferences(userId);
		UserPreferencesPK userPreferencesPK = new UserPreferencesPK(userId, actionId);
		Assertions.assertEquals(
			expectedValue,
			Objects.requireNonNull(
				userPreferencesRepository.findById(userPreferencesPK).orElse(null)
			).getWeight()
		);
	}
	
	@ParameterizedTest
	@DisplayName("사용자 선호도 변경 테스트(초기값 50)")
	@CsvSource({
		"1, 1, 10, 60",
		"1, 2, 10, 60",
		"2, 1, 20, 70"
	})
	public void changeUserPreferencesTest(int userId, int actionId, int changeValue, int expectedValue) {
		commandActionService.changeUserPreferences(userId, actionId, changeValue);
		UserPreferencesPK userPreferencesPK = new UserPreferencesPK(userId, actionId);
		Assertions.assertEquals(
			expectedValue,
			Objects.requireNonNull(
				userPreferencesRepository.findById(userPreferencesPK).orElse(null)
			).getWeight()
		);
	}
	
	@ParameterizedTest
	@DisplayName("추천 행동 태그 제외 테스트(초기값 50)")
	@ArgumentsSource(excludeActionTagListTestArgumentsProvider.class)
	public void excludeActionTagListTest(int userId, List<Integer> actionIdList, int expectedValue) {
		commandActionService.excludeActionTagList(userId, actionIdList);
		for (int actionId : actionIdList) {
			UserPreferencesPK userPreferencesPK = new UserPreferencesPK(userId, actionId);
			Assertions.assertEquals(
				expectedValue,
				Objects.requireNonNull(
					userPreferencesRepository.findById(userPreferencesPK).orElse(null)
				).getWeight()
			);
		}
	}
	
	static class excludeActionTagListTestArgumentsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			List<Integer> actionIdList = new ArrayList<>();
			actionIdList.add(1);
			actionIdList.add(2);
			actionIdList.add(3);
			return Stream.of(
				Arguments.of(1, actionIdList, 0)
			);
		}
	}
}
