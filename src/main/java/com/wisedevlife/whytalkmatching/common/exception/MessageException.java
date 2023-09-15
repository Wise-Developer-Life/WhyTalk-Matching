package com.wisedevlife.whytalkmatching.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageException extends RuntimeException {
    private final int code;

    public MessageException(int code, String message) {
        super(message);
        this.code = code;
    }
}
