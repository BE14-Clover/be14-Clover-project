package com.clover.moodiary.api.freepik.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FreepikConfig {

    @Value("${freepik.api.url}")
    private String apiUrl;

    @Value("${freepik.api.key}")
    private String apiKey;
}