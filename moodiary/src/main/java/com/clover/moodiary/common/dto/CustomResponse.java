package com.clover.moodiary.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public static <T> CustomResponse<T> success(T data) {
        return CustomResponse.<T>builder()
                .success(true)
                .data(data)
                .message(null)
                .build();
    }

    public static <T> CustomResponse<T> fail(String message) {
        return CustomResponse.<T>builder()
                .success(false)
                .data(null)
                .message(message)
                .build();
    }
}