package com.clover.moodiary.action.query.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.clover.moodiary.action.command.application.dto.ActionTagDTO;
import com.clover.moodiary.action.command.application.dto.RecommendedActionDTO;
import com.clover.moodiary.action.command.application.dto.TaggedRecommendedActionsDTO;
import com.clover.moodiary.action.command.application.dto.UserPreferencesDTO;

@Mapper
public interface ActionMapper {
	List<ActionTagDTO> selectActionTagIdList();
	
	RecommendedActionDTO selectRecommendedActionById(int actionId);
	
	List<TaggedRecommendedActionsDTO> selectActionTagByActionId(int actionId);
	
	UserPreferencesDTO selectUserPreferencesByActionTagId(int param1, int parma2);
	
	ActionTagDTO selectParentActionTagByActionTag(int actionTagId);
	
	List<ActionTagDTO> selectActionTagByParentActionId(int parentActionTagId);
	
	List<TaggedRecommendedActionsDTO> selectRecommendedActionIdByActionTagId(int randomActionTagId);
}
