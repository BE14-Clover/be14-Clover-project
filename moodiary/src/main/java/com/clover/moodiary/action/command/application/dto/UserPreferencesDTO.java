package com.clover.moodiary.action.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserPreferencesDTO {
	private int userId;
	private int actionTagId;
	private int weight;
	private java.util.Date lastRecommendedAt;
}
