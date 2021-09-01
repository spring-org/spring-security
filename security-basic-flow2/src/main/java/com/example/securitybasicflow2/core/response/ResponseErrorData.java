package com.example.securitybasicflow2.core.response;

import lombok.Builder;

public class ResponseErrorData {

    private final String message;
    private final int status;

    @Builder
    public ResponseErrorData(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
