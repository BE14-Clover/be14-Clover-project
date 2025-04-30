package com.clover.moodiary.api.freepik.controller;

import com.clover.moodiary.api.freepik.service.FreepikService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freepik")
@RequiredArgsConstructor
public class FreepikController {

    private final FreepikService freepikService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/search")
    public ResponseEntity<JsonNode> search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category) {
        try {
            String response = freepikService.searchImages(query, page, type, category);
            JsonNode json = objectMapper.readTree(response);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}