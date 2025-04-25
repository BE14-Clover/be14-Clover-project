package com.clover.moodiary.action.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ActionService {
	List<Integer> getAllActionTagIds();
}
