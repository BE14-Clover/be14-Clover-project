package com.clover.moodiary.action.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clover.moodiary.action.command.application.dto.ActionTagDTO;
import com.clover.moodiary.action.command.application.dto.RecommendedActionDTO;
import com.clover.moodiary.action.command.application.dto.TaggedRecommendedActionsDTO;
import com.clover.moodiary.action.command.application.dto.UserPreferencesDTO;

@Service
public interface ActionService {
	List<ActionTagDTO> getAllActionTagIds();
	
	RecommendedActionDTO getRecommendedActionById(int actionId);
	
	List<TaggedRecommendedActionsDTO> getActionTagByActionId(int actionId);
	
	UserPreferencesDTO getUserPreferencesByActionTagId(int userId, int actionTagId);
	
	ActionTagDTO getParentActionTagByActionTag(int actionTagId);
	
	List<RecommendedActionDTO> getThreeActions(int userId) throws RuntimeException;
	
	List<ActionTagDTO> searchActionTagListByActionTagName(String keyword);
}
