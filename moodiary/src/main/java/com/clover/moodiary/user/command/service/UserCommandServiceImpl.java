package com.clover.moodiary.user.command.service.impl;

import com.clover.moodiary.user.command.dto.*;
import com.clover.moodiary.user.command.entity.PasswordResetToken;
import com.clover.moodiary.user.command.entity.RegisterQuestion;
import com.clover.moodiary.user.command.entity.User;
import com.clover.moodiary.user.command.repository.PasswordResetTokenRepository;
import com.clover.moodiary.user.command.repository.RegisterQuestionRepository;
import com.clover.moodiary.user.command.repository.UserRepository;
import com.clover.moodiary.user.command.service.UserCommandService;
import com.clover.moodiary.user.command.util.JwtUtil;
import com.clover.moodiary.user.command.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
	private final UserRepository userRepo;
	private final RegisterQuestionRepository questionRepo;
	private final PasswordResetTokenRepository tokenRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final MailUtil mailUtil;

	@Override
	@Transactional
	public void register(RegisterRequest dto) {
		// 1) 질문 엔티티 조회
		RegisterQuestion q = questionRepo.findById(dto.getRegisterQuestionsId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 질문 ID"));

		// 2) User 엔티티 생성 & 저장
		User u = User.builder()
			.name(dto.getName())
			.email(dto.getEmail())
			.password(passwordEncoder.encode(dto.getPassword()))
			.phoneNumber(dto.getPhoneNumber())
			.registerQuestion(q)
			.answer(dto.getAnswer())
			.build();

		userRepo.save(u);
	}

	@Override
	public void deleteAccount(int userId) {
		userRepo.findById(userId)
			.ifPresent(u -> {
				u.setDeleted(true);
				userRepo.save(u);
			});
	}

	@Override
	public LoginResponse login(LoginRequest dto) {
		User u = userRepo.findByEmailAndDeletedFalse(dto.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일"));
		if (!passwordEncoder.matches(dto.getPassword(), u.getPassword())) {
			throw new IllegalArgumentException("비밀번호 불일치");
		}
		String token = jwtUtil.generateToken(u.getId(), u.getEmail());
		return new LoginResponse(token);
	}

	@Override
	public void logout(String authToken) {
		jwtUtil.invalidateToken(authToken);
	}

	@Override
	@Transactional
	public void requestPasswordReset(PasswordResetRequest dto) {
		User u = userRepo.findByEmailAndDeletedFalse(dto.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일"));

		// 이전 토큰 전부 삭제
		tokenRepo.deleteAllByUser(u);

		// 새 토큰 생성
		String newToken = UUID.randomUUID().toString();
		PasswordResetToken prt = PasswordResetToken.builder()
			.token(newToken)
			.expiresAt(LocalDateTime.now().plusHours(1))
			.user(u)
			.build();
		tokenRepo.save(prt);

		String resetLink = "http://localhost:8080/user/command/reset-password?token=" + newToken;
		mailUtil.sendEmail(u.getEmail(),
			"[Moodiary] 비밀번호 재설정 안내",
			"아래 링크를 클릭하여 비밀번호를 재설정해 주세요:\n" + resetLink
		);
	}

	@Override
	@Transactional
	public void resetPassword(PasswordReset dto) {
		PasswordResetToken prt = tokenRepo.findByToken(dto.getToken())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰"));
		if (prt.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("토큰이 만료되었습니다.");
		}

		User u = prt.getUser();
		u.setPassword(passwordEncoder.encode(dto.getNewPassword()));

		// 사용된 토큰 삭제
		tokenRepo.delete(prt);
	}

	@Override
	@Transactional
	public void updateUser(UpdateUserRequest dto) {
		User u = userRepo.findById(dto.getId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));
		if (dto.getName() != null)        u.setName(dto.getName());
		if (dto.getEmail() != null)       u.setEmail(dto.getEmail());
		if (dto.getPhoneNumber() != null) u.setPhoneNumber(dto.getPhoneNumber());
		if (dto.getNewPassword() != null) {
			u.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		}
		userRepo.save(u);
	}
}
