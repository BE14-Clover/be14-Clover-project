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
			.csrf(csrf -> csrf.disable())
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/user/command/login", "/user/command/register", "/user/command/reset-password").permitAll()
				.anyRequest().authenticated()
			)
			// JWT 필터를 사용자명/암호 필터 전에 끼워 넣음
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
				org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}
}
