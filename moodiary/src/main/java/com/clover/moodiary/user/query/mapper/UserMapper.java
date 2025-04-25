package com.clover.moodiary.user.query.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.clover.moodiary.user.query.dto.RequestEmailDTO;
import com.clover.moodiary.user.query.dto.RequestPasswordDTO;
import com.clover.moodiary.user.query.dto.UserDTO;
import com.clover.moodiary.user.query.dto.UserEmailDTO;
import com.clover.moodiary.user.query.dto.UserPasswordDTO;

@Mapper
public interface UserMapper {

	UserEmailDTO selectEmail(RequestEmailDTO request);

	UserPasswordDTO selectPassword(RequestPasswordDTO request);

	UserDTO selectInfo(int userId);
}
