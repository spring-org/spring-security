package com.example.securitybasicflow2.application.exception;

public class DuplicateMemberEmailException extends RuntimeException {

    public DuplicateMemberEmailException(String message) {
        super(message);
    }
}
