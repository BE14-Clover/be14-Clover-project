// com.clover.moodiary.user.command.service.impl/UserCommandServiceImpl.java
package com.clover.moodiary.user.command.service.impl;

import com.clover.moodiary.user.command.dto.LoginRequest;
import com.clover.moodiary.user.command.dto.LoginResponse;
import com.clover.moodiary.user.command.dto.PasswordReset;
import com.clover.moodiary.user.command.dto.PasswordResetRequest;
import com.clover.moodiary.user.command.dto.RegisterRequest;
import com.clover.moodiary.user.command.entity.PasswordResetToken;
import com.clover.moodiary.user.command.entity.User;
import com.clover.moodiary.user.command.repository.PasswordResetTokenRepository;
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
	// 토큰 레포지토리 주입
	private final PasswordResetTokenRepository tokenRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final MailUtil mailUtil;

	@Override
	public void register(RegisterRequest dto) {
		User u = User.builder()
			.name(dto.getName())
			.email(dto.getEmail())
			.phoneNumber(dto.getPhoneNumber())
			.password(passwordEncoder.encode(dto.getPassword()))
			.build();
		userRepo.save(u);
	}

	@Override
	public void deleteAccount(int userId) {
		userRepo.findById(userId).ifPresent(u -> {
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
		// 1) 유저 조회
		User u = userRepo.findByEmailAndDeletedFalse(dto.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일"));

		// 2) 기존 토큰 삭제
		u.setResetToken(null);
		userRepo.saveAndFlush(u);

		// 3) 새 토큰 생성 & 연결
		String newToken = UUID.randomUUID().toString();
		PasswordResetToken prt = PasswordResetToken.builder()
			.token(newToken)
			.expiresAt(LocalDateTime.now().plusHours(1))
			.user(u)
			.build();
		u.setResetToken(prt);
		userRepo.save(u);

		// 4) 메일 발송
		String resetLink = "http://localhost:8080/user/command/reset-password?token=" + newToken;
		mailUtil.sendEmail(u.getEmail(),
			"[Moodiary] 비밀번호 재설정 안내",
			"아래 링크를 클릭하여 비밀번호를 재설정해 주세요:\n" + resetLink
		);
	}

	@Override
	@Transactional
	public void resetPassword(PasswordReset dto) {
		// 1) 토큰 조회 및 유효성 검사
		PasswordResetToken prt = tokenRepo.findByToken(dto.getToken())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰"));
		if (prt.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("토큰이 만료되었습니다.");
		}

		// 2) 비밀번호 갱신
		User u = prt.getUser();
		u.setPassword(passwordEncoder.encode(dto.getNewPassword()));

		// 3) 토큰 삭제 (orphanRemoval 처리)
		u.setResetToken(null);
		userRepo.save(u);
	}
}
