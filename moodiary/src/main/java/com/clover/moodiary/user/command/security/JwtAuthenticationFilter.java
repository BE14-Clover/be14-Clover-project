package com.clover.moodiary.user.command.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.clover.moodiary.user.command.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
		HttpServletResponse res,
		FilterChain chain) throws ServletException, IOException {
		String header = req.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				Jws<Claims> claimsJws = jwtUtil.validateAndParse(token);
				Claims body = claimsJws.getBody();
				Integer userId = Integer.valueOf(body.getSubject());
				// Authentication 객체에 principal로 userId 저장 (authorities는 비워둠)
				UsernamePasswordAuthenticationToken auth =
					new UsernamePasswordAuthenticationToken(userId, null, null);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (JwtException e) {
				// 토큰이 유효하지 않으면 인증 무효 → 단순 무시(혹은 401 처리)
			}
		}
		chain.doFilter(req, res);
	}
}
