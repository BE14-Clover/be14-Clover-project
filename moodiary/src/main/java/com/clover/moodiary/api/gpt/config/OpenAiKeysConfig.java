package com.clover.moodiary.api.gpt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "openai.keys")
public class OpenAiKeysConfig {
    private String positiveScore;
    private String neutralScore;
    private String negativeScore;
    private String totalScore;
    private String emotion1;
    private String emotion2;
    private String emotion3;
    private String diaryTitle;
}