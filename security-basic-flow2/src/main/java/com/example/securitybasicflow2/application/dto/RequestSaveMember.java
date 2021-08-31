package com.example.securitybasicflow2.application.dto;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import lombok.Data;

@Data
public class RequestSaveMember {

    private String email;
    private String name;
    private String password;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
