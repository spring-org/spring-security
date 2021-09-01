package com.example.securitybasicflow2.application.exception;

public class DuplicateMemberEmailException extends BusinessException {

    public DuplicateMemberEmailException(int code, String errMessage) {
        super(code, errMessage);
    }
}
