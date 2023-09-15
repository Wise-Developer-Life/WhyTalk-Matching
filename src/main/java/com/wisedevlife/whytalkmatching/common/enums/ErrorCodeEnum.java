package com.wisedevlife.whytalkmatching.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    SYSTEM_ERROR(-1, "system error"),
    CHATROOM_NOT_FOUND_ERROR(404, "chatroom not found"),
    CHATROOM_EXISTED_ERROR(409, "chatroom is already existed");

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
