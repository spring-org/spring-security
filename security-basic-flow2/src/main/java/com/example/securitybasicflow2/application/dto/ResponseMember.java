package com.example.securitybasicflow2.application.dto;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import lombok.Data;

@Data
public class ResponseMember {
    private Long memberId;
    private String email;
    private String password;
    private String name;
    private MemberEntity.Role role;
}
