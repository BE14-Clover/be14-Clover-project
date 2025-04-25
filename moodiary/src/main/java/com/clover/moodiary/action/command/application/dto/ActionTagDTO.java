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
public class ActionTagDTO {
	private int id;
	private String name;
	private Integer parentActionTagId;
}
