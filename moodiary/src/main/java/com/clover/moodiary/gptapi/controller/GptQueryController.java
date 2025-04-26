package com.clover.moodiary.gptapi.controller;

import com.clover.moodiary.gptapi.query.dto.GptResultResponse;
import com.clover.moodiary.gptapi.query.service.GptQueryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt")
public class GptQueryController {

    private final GptQueryService gptQueryService;

    public GptQueryController(GptQueryService gptQueryService) {
        this.gptQueryService = gptQueryService;
    }

    @GetMapping("/fetch")
    public GptResultResponse fetchGptResult(@RequestParam String prompt) {
        return gptQueryService.fetchGptResult(prompt);
    }
}