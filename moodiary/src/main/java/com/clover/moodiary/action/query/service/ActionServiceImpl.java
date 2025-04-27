package com.clover.moodiary.action.query.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.clover.moodiary.action.command.application.dto.ActionTagDTO;
import com.clover.moodiary.action.command.application.dto.RecommendedActionDTO;
import com.clover.moodiary.action.command.application.dto.TaggedRecommendedActionsDTO;
import com.clover.moodiary.action.command.application.dto.UserPreferencesDTO;
import com.clover.moodiary.action.query.mapper.ActionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionServiceImpl implements ActionService {
	
	private final ActionMapper actionMapper;
	
	@Override
	public List<ActionTagDTO> getAllActionTagIds() {
		return actionMapper.selectActionTagIdList();
	}
	
	@Override
	public RecommendedActionDTO getRecommendedActionById(int actionId) {
		return actionMapper.selectRecommendedActionById(actionId);
	}
	
	@Override
	public List<TaggedRecommendedActionsDTO> getActionTagByActionId(int actionId) {
		return actionMapper.selectActionTagByActionId(actionId);
	}
	
	@Override
	public UserPreferencesDTO getUserPreferencesByActionTagId(int userId, int actionTagId) {
		return actionMapper.selectUserPreferencesByActionTagId(userId, actionTagId);
	}
	
	@Override
	public ActionTagDTO getParentActionTagByActionTag(int actionTagId) {
		return actionMapper.selectParentActionTagByActionTag(actionTagId);
	}
	
	@Override
	public List<RecommendedActionDTO> getThreeActions(int userId) {
		List<RecommendedActionDTO> recommendList = new ArrayList<>();
		while (recommendList.size() < 3) {
			int randomActionTagId = getRandomActionId(userId, 0);
			
			/* 설명. 중복 체크...입니다... */
			if (recommendList.size() == 1) {
				if (randomActionTagId == recommendList.get(0).getId()) continue;
			} else if (recommendList.size() == 2) {
				if (randomActionTagId == recommendList.get(0).getId()) continue;
				if (randomActionTagId == recommendList.get(1).getId()) continue;
			}
			recommendList.add(getRecommendedActionById(randomActionTagId));
		}
		return recommendList;
	}
	
	/* 설명. 가중치 기반 랜덤 추천 행동 뽑기 */
	private int getRandomActionId(int userId, int randomActionTagId) {
		List<ActionTagDTO> actionTagDTOList = actionMapper.selectActionTagByParentActionId(randomActionTagId);
		if (actionTagDTOList.isEmpty()) {
			
			/* 설명. 하위 태그 없을 시 */
			return getRandomActionByTagId(randomActionTagId);
		} else {
			
			/* 설명. 하위 태그 있을 시 */
			List<UserPreferencesDTO> userPreferencesDTOList = new ArrayList<>();
			while (!actionTagDTOList.isEmpty()) {
				userPreferencesDTOList.add(actionMapper.selectUserPreferencesByActionTagId(userId, actionTagDTOList.get(0).getId()));
				actionTagDTOList.remove(0);
			}
			int parentActionTagId = getWeightedRandomActionTagId(userPreferencesDTOList);
			return getRandomActionId(userId, parentActionTagId);
		}
	}
	
	private int getWeightedRandomActionTagId(List<UserPreferencesDTO> userPreferencesDTOList) {
		int totalWeight = 0;
		for (UserPreferencesDTO userPreferencesDTO : userPreferencesDTOList) {
			totalWeight += userPreferencesDTO.getWeight();
		}
		int randomValue = (int) (Math.random() * totalWeight);
		
		int checkWeight = 0;
		for (UserPreferencesDTO userPreferencesDTO : userPreferencesDTOList) {
			checkWeight += userPreferencesDTO.getWeight();
			if (checkWeight >= randomValue) {
				return userPreferencesDTO.getActionTagId();
			}
		}
		throw new RuntimeException("getWeightedRandomActionTagId failure");
	}
	
	/* 설명. 마지막 태그에서 랜덤 행동 (가중치 x) */
	/* 설명. 마지막 태그에 속한 행동들 중 어떤 행동은 다른 태그를 가지고, 어떤 행동은 다른 태그를 갖지 않는다면, 다른 태그에 기반한 랜덤 어떻게? */
	private int getRandomActionByTagId(int randomActionTagId) {
		List<TaggedRecommendedActionsDTO> actionList = actionMapper.selectRecommendedActionIdByActionTagId(randomActionTagId);
		log.info(actionList.toString());
		return actionList.get((int) (Math.random() * actionList.size())).getRecommendedActionsId();
	}
}
