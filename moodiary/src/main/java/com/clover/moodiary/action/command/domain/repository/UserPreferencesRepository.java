package com.clover.moodiary.action.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clover.moodiary.action.command.domain.aggregate.entity.UserPreferences;
import com.clover.moodiary.action.command.domain.aggregate.entity.UserPreferencesPK;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, UserPreferencesPK> {
}
