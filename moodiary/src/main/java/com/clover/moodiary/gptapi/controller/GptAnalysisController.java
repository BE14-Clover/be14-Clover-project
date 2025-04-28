package com.clover.moodiary.gptapi.controller;

import com.clover.moodiary.gptapi.service.GptAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
public class GptAnalysisController {

    private final GptAnalysisService gptAnalysisService;

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeDiary(@RequestBody String diaryContent) {
        String result = gptAnalysisService.analyzeDiary(diaryContent);
        return ResponseEntity.ok(result);
    }
}