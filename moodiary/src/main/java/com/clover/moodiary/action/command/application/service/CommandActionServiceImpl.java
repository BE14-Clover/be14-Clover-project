package com.clover.moodiary.action.command.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.clover.moodiary.action.command.application.dto.ActionTagDTO;
import com.clover.moodiary.action.command.application.dto.TaggedRecommendedActionsDTO;
import com.clover.moodiary.action.command.application.dto.UserPreferencesDTO;
import com.clover.moodiary.action.command.domain.aggregate.entity.UserPreferences;
import com.clover.moodiary.action.command.domain.repository.UserPreferencesRepository;
import com.clover.moodiary.action.query.service.ActionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandActionServiceImpl implements CommandActionService {
	
	private final ActionService actionService;
	private final UserPreferencesRepository userPreferencesRepository;
	
	@Override
	public void insertInitialUserPreferences(int userId) {
		List<ActionTagDTO> actionTagIdList = actionService.getAllActionTagIds();
		
		for (ActionTagDTO actionTagDTO : actionTagIdList) {
			UserPreferences userPreferences = new UserPreferences();
			userPreferences.setUserId(userId);
			userPreferences.setActionTagId(actionTagDTO.getId());
			userPreferences.setWeight(50);
			userPreferences.setLastRecommendedAt(new java.util.Date());
			userPreferencesRepository.save(userPreferences);
		}
	}
	
	@Override
	public void plusUserPreferences(int userId, int actionId) {
		/* 설명. 가중치 +할 값 */
		int plusValue = 10;
		
		List<TaggedRecommendedActionsDTO> taggedRecommendedActionsDTOList = actionService.getActionTagByActionId(actionId);
		List<UserPreferencesDTO> userPreferencesDTOList = new ArrayList<>();
		UserPreferencesDTO upDTO;
		
		/* 설명. 주어진 행동의 태그들을 가중치 추가한 채로 우선 리스트에 담기 */
		while (!taggedRecommendedActionsDTOList.isEmpty()) {
			upDTO = actionService.getUserPreferencesByActionTagId(userId, taggedRecommendedActionsDTOList.get(0).getActionTagId());
			upDTO.setWeight(upDTO.getWeight() + plusValue);
			userPreferencesDTOList.add(upDTO);
			taggedRecommendedActionsDTOList.remove(0);
		}
		
		/* 설명. 주어진 회원의 가중치에, 주어진 행동의 태그와 상위 태그들을 가중치 추가한 채로 리스트에 담기 */
		int index = 0;
		ActionTagDTO atDTO;
		while (userPreferencesDTOList.get(index) != null) {
			/* 설명. 부모 태그 가져오기 */
			atDTO = actionService.getParentActionTagByActionTag(userPreferencesDTOList.get(index).getActionTagId());
			if (atDTO == null) break;
			
			/* 설명. 회원별 가져온 부모 태그의 가중치 가져오기 */
			upDTO = actionService.getUserPreferencesByActionTagId(userId, atDTO.getId());
			
			if (upDTO != null) {
				upDTO.setWeight(upDTO.getWeight() + plusValue);
				userPreferencesDTOList.add(upDTO);
			}
			index++;
		}
		
		/* 설명. 리스트의 값들을 하나씩 DB에 반영 */
		while (!userPreferencesDTOList.isEmpty()) {
			upDTO = userPreferencesDTOList.get(0);
			UserPreferences userPreferences = new UserPreferences();
			userPreferences.setUserId(upDTO.getUserId());
			userPreferences.setActionTagId(upDTO.getActionTagId());
			userPreferences.setWeight(upDTO.getWeight());
			userPreferences.setLastRecommendedAt(new java.util.Date());
			userPreferencesRepository.save(userPreferences);
			userPreferencesDTOList.remove(0);
		}
	}
}
