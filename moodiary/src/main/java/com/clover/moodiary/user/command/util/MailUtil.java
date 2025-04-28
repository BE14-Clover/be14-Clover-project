// com.clover.moodiary.user.command.util/MailUtil.java
package com.clover.moodiary.user.command.util;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {
	private final JavaMailSender mailSender;

	public void sendEmail(String to, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			// 두번째 파라미터는 multipart 여부, 세번째가 인코딩
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			helper.setFrom("rhtjddus0502@naver.com", "Moodiary");

			mailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("메일 발송에 실패했습니다.", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
