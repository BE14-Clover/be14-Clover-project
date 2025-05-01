package com.clover.moodiary.user.command.util;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailUtil {
	private final JavaMailSender mailSender;

	public void sendEmail(String to, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText("<html><body>" + body + "</body></html>", true);
			helper.setFrom("rhtjddus0502@naver.com", "Moodiary");

			mailSender.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new RuntimeException("메일 발송에 실패했습니다.", e);
		}
	}
}
