package com.clover.moodiary.user.command.config;

import com.clover.moodiary.user.command.security.JwtAuthenticationFilter;
import com.clover.moodiary.user.command.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtUtil jwtUtil;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			// CSRF 비활성화 (stateless API 용)
			.csrf(csrf -> csrf.disable())
			// 세션을 생성하지 않도록 설정
			.sessionManagement(sm -> sm
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			// 엔드포인트별 권한 설정
			.authorizeHttpRequests(auth -> auth
				// 1) 로그인/회원가입/비밀번호 재설정 요청은 모두 허용
				.requestMatchers(
					"/user/command/login",
					"/user/command/logout",
					"/user/command/register",
					"/user/command/request-password-reset",
					"/user/command/reset-password",
					// 아이디 찾기 API (public 으로 열려있어야 한다면)
					"/user/query/email"
				).permitAll()
				// 2) 그 외 모든 요청은 인증된 사용자만
				.anyRequest().authenticated()
			)
			// JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
			.addFilterBefore(
				new JwtAuthenticationFilter(jwtUtil),
				UsernamePasswordAuthenticationFilter.class
			)
			// 기본 HTTP Basic 은 그대로 두되, 주로 JWT 만 사용
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}
