package com.example.securitybasicflow2.application.exception;

import com.example.securitybasicflow2.core.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(int code, String errMessage) {
        super(code, errMessage);
    }
}
