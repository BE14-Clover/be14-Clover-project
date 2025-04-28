// com.clover.moodiary.user.command.util/MailUtil.java
package com.clover.moodiary.user.command.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {
	private final JavaMailSender mailSender;

	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(text);
		mailSender.send(msg);
	}
}
