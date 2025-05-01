package com.clover.moodiary.user.query.service;

import com.clover.moodiary.user.query.dto.RequestEmailDTO;
import com.clover.moodiary.user.query.dto.RequestPasswordDTO;
import com.clover.moodiary.user.query.dto.UserDTO;
import com.clover.moodiary.user.query.dto.UserEmailDTO;
import com.clover.moodiary.user.query.dto.UserPasswordDTO;
import com.clover.moodiary.user.query.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock UserMapper mapper;
	@InjectMocks UserServiceImpl service;

	@Test
	void findEmail_매핑된_DTO_반환() {
		// given
		RequestEmailDTO req = new RequestEmailDTO();
		req.setName("홍길동");
		req.setPhoneNumber("01012345678");
		req.setRegisterQuestionsId(String.valueOf(1L));
		req.setAnswer("답변");

		UserEmailDTO dto = new UserEmailDTO();
		dto.setEmail("email@domain.com");

		given(mapper.selectEmail(req)).willReturn(dto);

		// when
		UserEmailDTO result = service.findEmail(req);

		// then
		assertSame(dto, result);
	}

	@Test
	void findPassword_매핑된_DTO_반환() {
		// given
		RequestPasswordDTO req = new RequestPasswordDTO();
		req.setEmail("email@domain.com");

		UserPasswordDTO dto = new UserPasswordDTO();
		dto.setPassword("hashedPw");

		given(mapper.selectPassword(req)).willReturn(dto);

		// when
		UserPasswordDTO result = service.findPassword(req);

		// then
		assertSame(dto, result);
	}

	@Test
	void findInfo_매핑된_UserDTO_반환() {
		// given
		UserDTO dto = new UserDTO();
		dto.setId(5);
		dto.setEmail("e@d.com");
		dto.setName("이름");
		dto.setPhoneNumber("010-0000-0000");

		given(mapper.selectInfo(5)).willReturn(dto);

		// when
		UserDTO result = service.findInfo(5);

		// then
		assertSame(dto, result);
	}
}
