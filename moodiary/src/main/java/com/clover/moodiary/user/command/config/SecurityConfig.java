package com.clover.moodiary.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
	/**
	 * BCryptPasswordEncoder 빈 등록
	 * → 회원가입, 로그인, 비밀번호 검증 시 사용
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
