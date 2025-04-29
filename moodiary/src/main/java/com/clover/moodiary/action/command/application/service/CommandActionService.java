package com.clover.moodiary.action.command.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CommandActionService {
	void insertInitialUserPreferences(int userId);
	
	void changeUserPreferences(int userId, int actionId, int changeValue);
	
	void excludeActionTagList(int userId, List<Integer> excludingActionTagList);
}
