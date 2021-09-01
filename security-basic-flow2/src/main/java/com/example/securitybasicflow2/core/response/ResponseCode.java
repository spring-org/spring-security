package com.example.securitybasicflow2.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    OK(200, "데이터 전송에 성공하였습니다."),
    ERROR(400, "데이터 전송에 실패하였습니다.");

    private int code;
    private String desc;
}