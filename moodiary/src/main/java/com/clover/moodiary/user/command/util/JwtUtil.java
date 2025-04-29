// 위치: com.clover.moodiary.user.command.util/JwtUtil.java
package com.clover.moodiary.user.command.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.clover.moodiary.user.command.config.JwtProperties;

@Service
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtProperties props;
	private SecretKey key;

	/** 프로퍼티에서 읽은 secret 으로 SecretKey 인스턴스 생성 */
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 유저ID, 이메일을 담아 액세스토큰 발급
	 * - subject: userId
	 * - claim "email"
	 * - 발행일, 만료일 설정
	 * - HS256 서명
	 */
	public String generateToken(Integer userId, String email) {
		Date now = new Date();
		return Jwts.builder()
			.setIssuer(props.getIssuer())
			.setSubject(userId.toString())
			.claim("email", email)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + props.getAccessTokenExpirationMs()))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	/**
	 * 토큰 검증 & 파싱
	 * 예: 로그인 필터 안에서
	 */
	public Jws<Claims> validateAndParse(String token) throws JwtException {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
	}

	/** (옵션) 토큰 블랙리스트 로직이 필요하다면 추가 메서드 구현 */
	public void invalidateToken(String token) {
		// 예: Redis에 블랙리스트로 저장
	}
}
