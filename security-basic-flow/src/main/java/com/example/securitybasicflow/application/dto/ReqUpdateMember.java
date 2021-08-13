package com.example.securitybasicflow.application.dto;

import com.example.securitybasicflow.security.domain.RoleType;
import lombok.Data;

@Data
public class ReqUpdateMember {

    private String email;
    private String username;
    private RoleType role;

}
