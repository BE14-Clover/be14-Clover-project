package com.clover.moodiary.user.command.service;

import com.clover.moodiary.pets.command.domain.aggregate.entity.PetEntity;
import com.clover.moodiary.pets.command.domain.aggregate.entity.UserPetEntity;
import com.clover.moodiary.pets.command.domain.repository.PetRepository;
import com.clover.moodiary.pets.command.domain.repository.UserPetRepository;
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

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
	private final UserRepository userRepo;
	private final RegisterQuestionRepository questionRepo;
	private final PasswordResetTokenRepository tokenRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final MailUtil mailUtil;
	private final UserPetRepository userPetRepository;
	private final PetRepository petRepository;

	@Override
	@Transactional
	public void register(RegisterRequest dto) {

		RegisterQuestion q = questionRepo.findById(dto.getRegisterQuestionsId())
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 질문 ID"));

		User u = User.builder()
			.name(dto.getName())
			.email(dto.getEmail())
			.password(passwordEncoder.encode(dto.getPassword()))
			.phoneNumber(dto.getPhoneNumber())
			.registerQuestion(q)
			.answer(dto.getAnswer())
			.build();

		userRepo.save(u);

		PetEntity pet = petRepository.findById(1)
			.orElseThrow(() -> new IllegalArgumentException("기본 펫이 존재하지 않습니다."));

		UserPetEntity userPet = new UserPetEntity(u.getId(), pet.getId());
		userPetRepository.save(userPet);
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
		UserDTO userDTO = new UserDTO(u.getId(), u.getEmail(), u.getName());

		return new LoginResponse(token, userDTO);
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

		String resetLink = "http://localhost:5173/reset-password?token=" + newToken;

		String htmlBody = """
				<div style="font-family: 'Arial', sans-serif; padding: 20px; background-color: #fff7ee;">
					<h2 style="color: #A17C59;">[Moodiary] 비밀번호 재설정 안내</h2>
					<p>안녕하세요,<br>아래 버튼을 클릭하여 비밀번호를 재설정해 주세요.</p>
					<a href="%s" style="
						display: inline-block;
						padding: 12px 24px;
						margin-top: 16px;
						background-color: #A17C59;
						color: white;
						text-decoration: none;
						font-weight: bold;
						border-radius: 8px;">
						🔐 비밀번호 재설정
					</a>
					<p style="margin-top: 20px; font-size: 12px; color: #888;">
						※ 만약 버튼이 동작하지 않는다면 아래 링크를 복사해 브라우저에 붙여넣기 해 주세요:<br>
						<a href="%s">%s</a>
					</p>
				</div>
			""".formatted(resetLink, resetLink, resetLink);

		mailUtil.sendEmail(
			u.getEmail(),
			"[Moodiary] 비밀번호 재설정 안내",
			htmlBody
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
		if (dto.getName() != null)
			u.setName(dto.getName());
		if (dto.getEmail() != null)
			u.setEmail(dto.getEmail());
		if (dto.getPhoneNumber() != null)
			u.setPhoneNumber(dto.getPhoneNumber());
		if (dto.getNewPassword() != null) {
			u.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		}
		userRepo.save(u);
	}
}
