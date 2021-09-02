package com.example.securitybasicflow2.application.dto;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Data
public class RequestUpdateMember {

    @NotEmpty(message = "email not be empty")
    private String email;

    @NotEmpty(message = "password not be empty")
    private String password;

    private String name;

    private MemberEntity.Role role;

    public boolean isName() {
        return !Strings.isEmpty(name);
    }

    public boolean isPassword() {
        return !Strings.isEmpty(password);
    }

    public boolean isRole() {
        return !Objects.isNull(role);
    }

    @Builder
    public RequestUpdateMember(String email, String password, String name, MemberEntity.Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
