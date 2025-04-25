package com.clover.moodiary.action.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPreferencesPK {
	private int userId;
	private int actionTagId;
}
