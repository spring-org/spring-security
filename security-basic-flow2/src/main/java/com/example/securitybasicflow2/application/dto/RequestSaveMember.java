package com.example.securitybasicflow2.application.dto;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RequestSaveMember {

    @NotEmpty(message = "email mat not be empty")
    private String email;
    @NotEmpty(message = "email mat not be empty")
    private String name;
    @NotEmpty(message = "email mat not be empty")
    private String password;

    @Builder
    public RequestSaveMember(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
