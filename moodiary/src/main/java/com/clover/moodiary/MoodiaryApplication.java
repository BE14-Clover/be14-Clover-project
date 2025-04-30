package com.clover.moodiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/* 설명. 시큐리티 설정 전까지 로그인 페이지 안 뜨게*/
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties
public class MoodiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodiaryApplication.class, args);
	}

}
