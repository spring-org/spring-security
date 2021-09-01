package com.example.securitybasicflow2.application.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final int code;
    private final String errMessage;

    protected BusinessException(int code, String errMessage) {
        this.code = code;
        this.errMessage = errMessage;
    }
}
