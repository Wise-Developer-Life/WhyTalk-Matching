package com.wisedevlife.whytalkmatching.common.exception;

import com.wisedevlife.whytalkmatching.common.enums.ErrorCodeEnum;
import com.wisedevlife.whytalkmatching.common.helper.ResponseHandler;
import com.wisedevlife.whytalkmatching.dto.response.ReturnResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ReturnResponse<Object>> globalExceptionHandler(Exception e) {
        log.error("Catching unknown runtime exception.", e);
        return ResponseHandler.badRequest(ErrorCodeEnum.SYSTEM_ERROR);
    }
}
