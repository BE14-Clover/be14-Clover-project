// com.clover.moodiary.user.command.service.impl/UserCommandServiceImpl.java
package com.clover.moodiary.user.command.service.impl;

import com.clover.moodiary.user.command.dto.*;
import com.clover.moodiary.user.command.entity.*;
import com.clover.moodiary.user.command.repository.*;
import com.clover.moodiary.user.command.service.UserCommandService;
import com.clover.moodiary.user.command.util.JwtUtil;
import com.clover.moodiary.user.command.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
	private final UserRepository userRepo;
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
		// JWT 블랙리스트에 올리거나, 세션 만료 처리
		jwtUtil.invalidateToken(authToken);
	}

	@Override
	public void requestPasswordReset(PasswordResetRequest dto) {
		User u = userRepo.findByEmailAndDeletedFalse(dto.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일"));

		// 토큰 생성
		String token = UUID.randomUUID().toString();
		PasswordResetToken prt = PasswordResetToken.builder()
			.token(token)
			.expiresAt(LocalDateTime.now().plusHours(1))
			.user(u)
			.build();
		tokenRepo.save(prt);

		// 이메일 보내기
		String resetLink = "https://your-domain.com/user/command/reset-password?token=" + token;
		mailUtil.sendEmail(u.getEmail(),
			"[Moodiary] 비밀번호 재설정 안내",
			"아래 링크를 클릭하여 비밀번호를 재설정해 주세요:\n" + resetLink);
	}

	@Override
	public void resetPassword(PasswordReset dto) {
		PasswordResetToken prt = tokenRepo.findByToken(dto.getToken())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰"));
		if (prt.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("토큰이 만료되었습니다.");
		}
		User u = prt.getUser();
		u.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		userRepo.save(u);

		// 사용된 토큰 삭제
		tokenRepo.delete(prt);
	}
}
