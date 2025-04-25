package com.clover.moodiary.action.command.application.service;

import org.springframework.stereotype.Service;

@Service
public interface CommandActionService {
	void insertInitialUserPreferences(int userId);
}
