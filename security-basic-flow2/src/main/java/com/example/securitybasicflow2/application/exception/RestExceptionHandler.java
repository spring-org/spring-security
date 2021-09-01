package com.example.securitybasicflow2.application.exception;

import com.example.securitybasicflow2.core.response.ResponseErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DuplicateMemberEmailException.class)
    public ResponseEntity<ResponseErrorData> duplicateMemberEmailException(BusinessException exc) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseErrorData.builder()
                        .status(exc.getCode())
                        .message(exc.getErrMessage())
                        .build());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ResponseErrorData> memberNotFoundException(BusinessException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseErrorData.builder()
                        .message(exc.getErrMessage())
                        .status(exc.getCode())
                        .build());
    }

}
