package com.clover.moodiary.action.command.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clover.moodiary.action.command.domain.aggregate.entity.UserPreferences;
import com.clover.moodiary.action.command.domain.repository.UserPreferencesRepository;
import com.clover.moodiary.action.query.service.ActionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandActionServiceImpl implements CommandActionService {
	
	private final ActionService actionService;
	private final UserPreferencesRepository userPreferencesRepository;
	
	@Override
	public void insertInitialUserPreferences(int userId) {
		List<Integer> actionTagIdList = actionService.getAllActionTagIds();
		
		for (int actionTagId : actionTagIdList) {
			UserPreferences userPreferences = new UserPreferences();
			userPreferences.setUserId(userId);
			userPreferences.setActionTagId(actionTagId);
			userPreferencesRepository.save(userPreferences);
		}
	}
}
