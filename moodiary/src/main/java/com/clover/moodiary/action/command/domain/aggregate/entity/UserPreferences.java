package com.clover.moodiary.action.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user_preferences")
@IdClass(UserPreferencesPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserPreferences {
	@Id
	@Column(name = "user_id")
	private int userId;
	
	@Id
	@Column(name = "action_tag_id")
	private int actionTagId;
	
	@Column(name = "weight")
	private int weight = 50;
	
	@Column(name = "last_recommended_at")
	private java.util.Date lastRecommendedAt;
}
