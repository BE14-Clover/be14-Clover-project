package com.clover.moodiary.gptapi.controller;

import com.clover.moodiary.gptapi.command.dto.SaveGptResultCommand;
import com.clover.moodiary.gptapi.command.service.GptCommandService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt")
public class GptCommandController {

    private final GptCommandService gptCommandService;

    public GptCommandController(GptCommandService gptCommandService) {
        this.gptCommandService = gptCommandService;
    }

    @PostMapping("/save")
    public void saveGptResult(@RequestBody SaveGptResultCommand command) {
        gptCommandService.saveGptResult(command);
    }
}