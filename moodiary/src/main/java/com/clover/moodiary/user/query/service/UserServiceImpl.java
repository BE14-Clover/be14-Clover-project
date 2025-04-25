package com.clover.moodiary.user.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clover.moodiary.user.query.dto.RequestEmailDTO;
import com.clover.moodiary.user.query.dto.RequestPasswordDTO;
import com.clover.moodiary.user.query.dto.UserDTO;
import com.clover.moodiary.user.query.dto.UserEmailDTO;
import com.clover.moodiary.user.query.dto.UserPasswordDTO;
import com.clover.moodiary.user.query.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public UserEmailDTO findEmail(RequestEmailDTO request) {
		return userMapper.selectEmail(request);
	}

	@Override
	public UserPasswordDTO findPassword(RequestPasswordDTO request) {
		return userMapper.selectPassword(request);
	}

	@Override
	public UserDTO findInfo(int userId) {
		return userMapper.selectInfo(userId);
	}
}
