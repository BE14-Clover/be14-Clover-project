package com.clover.moodiary.gptapi.service;

public class GptAnalysisException extends RuntimeException {
    public GptAnalysisException(String message) {
        super(message);
    }

    public GptAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}