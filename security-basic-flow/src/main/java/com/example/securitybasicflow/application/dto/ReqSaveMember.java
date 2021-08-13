package com.example.securitybasicflow.application.dto;

import lombok.Data;

@Data
public class ReqSaveMember {
    private String email;
    private String username;
    private String password;
}
