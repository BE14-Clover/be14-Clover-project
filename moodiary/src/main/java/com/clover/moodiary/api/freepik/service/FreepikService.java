package com.clover.moodiary.api.freepik.service;

import com.clover.moodiary.api.freepik.config.FreepikConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class FreepikService {

    private final FreepikConfig config;

    @PostConstruct
    public void init() {
        System.out.println("✅ Freepik API URL: " + config.getApiUrl());
        System.out.println("✅ Freepik API Key: " + config.getApiKey());
    }

    public String searchImages(String query, int page, String type, String category) {
        String encodedQuery = UriUtils.encode(query, StandardCharsets.UTF_8);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(config.getApiUrl())
                .queryParam("query", encodedQuery)
                .queryParam("page", page);

        if (type != null && !type.isEmpty()) {
            builder.queryParam("type", type); // 예: vector, icon, photo
        }

        if (category != null && !category.isEmpty()) {
            builder.queryParam("category", category); // 예: animals, business 등
        }

        String url = builder.build(true).toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-freepik-api-key", config.getApiKey());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Freepik API 호출 실패", e);
        }
    }
}