package com.example.securitybasicflow2.application.exception;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(int code, String errMessage) {
        super(code, errMessage);
    }
}
