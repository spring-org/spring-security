package com.example.securitybasicflow.application.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ReqLoginMember {
    private String email;
    private String password;

    public boolean isValid() {
        return StringUtils.hasText(email) && StringUtils.hasText(password);
    }
}
