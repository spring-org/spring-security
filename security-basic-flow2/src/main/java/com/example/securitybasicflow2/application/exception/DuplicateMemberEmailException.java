package com.example.securitybasicflow2.application.exception;

import com.example.securitybasicflow2.core.exception.BusinessException;

public class DuplicateMemberEmailException extends BusinessException {

    public DuplicateMemberEmailException(int code, String errMessage) {
        super(code, errMessage);
    }
}
