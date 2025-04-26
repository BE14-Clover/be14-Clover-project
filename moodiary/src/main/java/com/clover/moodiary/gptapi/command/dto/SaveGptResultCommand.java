package com.clover.moodiary.gptapi.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveGptResultCommand {
    private final String result;
}