package com.clover.moodiary.user.command.service;

import com.clover.moodiary.user.command.dto.*;
import com.clover.moodiary.user.command.entity.PasswordResetToken;
import com.clover.moodiary.user.command.entity.RegisterQuestion;
import com.clover.moodiary.user.command.entity.User;
import com.clover.moodiary.user.command.repository.PasswordResetTokenRepository;
import com.clover.moodiary.user.command.repository.RegisterQuestionRepository;
import com.clover.moodiary.user.command.repository.UserRepository;
import com.clover.moodiary.user.command.util.JwtUtil;
import com.clover.moodiary.user.command.util.MailUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceImplTest {

	@Mock UserRepository userRepo;
	@Mock RegisterQuestionRepository questionRepo;
	@Mock PasswordResetTokenRepository tokenRepo;
	@Mock BCryptPasswordEncoder passwordEncoder;
	@Mock JwtUtil jwtUtil;
	@Mock MailUtil mailUtil;

	@InjectMocks
	UserCommandServiceImpl service;

	RegisterQuestion sampleQuestion;
	User sampleUser;

	@BeforeEach
	void setup() {
		// 질문 엔티티
		sampleQuestion = RegisterQuestion.builder()
			.id(Long.valueOf("1"))
			.question("테스트 질문")
			.build();

		// 사용자 엔티티
		sampleUser = User.builder()
			.id(10)
			.email("test@domain.com")
			.password("encodedPw")
			.name("홍길동")
			.phoneNumber("010-1234-5678")
			.registerQuestion(sampleQuestion)
			.answer("답변")
			.build();
	}

	@Test
	void register_새로운_회원이_저장된다() {
		// given
		RegisterRequest dto = RegisterRequest.builder()
			.email("test@domain.com")
			.password("rawPw")
			.name("홍길동")
			.phoneNumber("010-1234-5678")
			.registerQuestionsId(1L)
			.answer("답변")
			.build();

		given(questionRepo.findById(1L))
			.willReturn(Optional.of(sampleQuestion));
		given(passwordEncoder.encode("rawPw"))
			.willReturn("encodedPw");

		// when
		service.register(dto);

		// then
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		then(userRepo).should().save(captor.capture());
		User saved = captor.getValue();
		assertEquals("test@domain.com", saved.getEmail());
		assertEquals("encodedPw", saved.getPassword());
		assertEquals("답변", saved.getAnswer());
		assertSame(sampleQuestion, saved.getRegisterQuestion());
	}

	@Test
	void deleteAccount_사용자_삭제_플래그가_세팅된다() {
		// given
		given(userRepo.findById(10)).willReturn(Optional.of(sampleUser));

		// when
		service.deleteAccount(10);

		// then: deleted=true 로 저장
		then(userRepo).should().save(argThat(u -> u.getDeleted()));
	}

	@Test
	void login_올바른_자격증명_로그인_성공() {
		// given
		LoginRequest req = LoginRequest.builder()
			.email("test@domain.com")
			.password("rawPw")
			.build();

		given(userRepo.findByEmailAndDeletedFalse("test@domain.com"))
			.willReturn(Optional.of(sampleUser));
		given(passwordEncoder.matches("rawPw", "encodedPw"))
			.willReturn(true);
		given(jwtUtil.generateToken(10, "test@domain.com"))
			.willReturn("jwt-token");

		// when
		LoginResponse resp = service.login(req);

		// then
		assertEquals("jwt-token", resp.getToken());
		assertEquals(10, resp.getUser().getId());
		assertEquals("홍길동", resp.getUser().getName());
	}

	@Test
	void login_잘못된_비밀번호_예외발생() {
		// given
		given(userRepo.findByEmailAndDeletedFalse("test@domain.com"))
			.willReturn(Optional.of(sampleUser));
		given(passwordEncoder.matches(any(), any()))
			.willReturn(false);

		// then
		assertThrows(IllegalArgumentException.class,
			() -> service.login(
				LoginRequest.builder()
					.email("test@domain.com")
					.password("wrong")
					.build()
			));
	}

	@Test
	void requestPasswordReset_토큰생성_이메일발송() {
		// given
		PasswordResetRequest dto = PasswordResetRequest.builder()
			.email("test@domain.com")
			.build();
		given(userRepo.findByEmailAndDeletedFalse("test@domain.com"))
			.willReturn(Optional.of(sampleUser));

		// when
		service.requestPasswordReset(dto);

		// then
		then(tokenRepo).should().deleteAllByUser(sampleUser);
		then(tokenRepo).should().save(any(PasswordResetToken.class));
		then(mailUtil).should().sendEmail(
			eq("test@domain.com"),
			contains("비밀번호 재설정 안내"),
			contains("reset-password?token=")
		);
	}

	@Test
	void resetPassword_유효한_토큰_비밀번호_변경() {
		// given
		String token = "abc123";
		PasswordResetToken prt = PasswordResetToken.builder()
			.token(token)
			.expiresAt(LocalDateTime.now().plusMinutes(10))
			.user(sampleUser)
			.build();
		given(tokenRepo.findByToken(token)).willReturn(Optional.of(prt));
		given(passwordEncoder.encode("newPw"))
			.willReturn("encodedNewPw");

		// when
		service.resetPassword(
			PasswordReset.builder()
				.token(token)
				.newPassword("newPw")
				.build()
		);

		// then
		assertEquals("encodedNewPw", sampleUser.getPassword());
		then(tokenRepo).should().delete(prt);
	}

	@Test
	void resetPassword_만료된_토큰_예외발생() {
		// given
		String token = "expired";
		PasswordResetToken prt = PasswordResetToken.builder()
			.token(token)
			.expiresAt(LocalDateTime.now().minusMinutes(1))
			.user(sampleUser)
			.build();
		given(tokenRepo.findByToken(token)).willReturn(Optional.of(prt));

		// then
		assertThrows(IllegalArgumentException.class,
			() -> service.resetPassword(
				PasswordReset.builder()
					.token(token)
					.newPassword("pw")
					.build()
			));
	}

	@Test
	void updateUser_필드_갱신_정상() {
		// given
		UpdateUserRequest dto = new UpdateUserRequest();
		dto.setId(10);
		dto.setName("새이름");
		dto.setPhoneNumber("010-0000-0000");
		dto.setNewPassword("pw2");

		given(userRepo.findById(10)).willReturn(Optional.of(sampleUser));
		given(passwordEncoder.encode("pw2")).willReturn("enc2");

		// when
		service.updateUser(dto);

		// then
		assertEquals("새이름", sampleUser.getName());
		assertEquals("010-0000-0000", sampleUser.getPhoneNumber());
		assertEquals("enc2", sampleUser.getPassword());
		then(userRepo).should().save(sampleUser);
	}
}
