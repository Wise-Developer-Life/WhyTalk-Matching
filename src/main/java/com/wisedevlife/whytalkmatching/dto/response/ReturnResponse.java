package com.wisedevlife.whytalkmatching.dto.response;

import java.time.Instant;

public record ReturnResponse<T>(int code, String message, boolean success, T data, Long time) {
    public static <T> ReturnResponse<T> of(int code, String message, boolean success, T data) {
        Long currentTime = Instant.now().getEpochSecond();
        return new ReturnResponse<>(code, message, success, data, currentTime);
    }
}
