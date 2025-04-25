package com.clover.moodiary.action.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.clover.moodiary.action.query.mapper.ActionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionServiceImpl implements ActionService {
	
	private final ActionMapper actionMapper;
	
	@Override
	public List<Integer> getAllActionTagIds() {
		return actionMapper.selectActionTagIdList();
	}
}
