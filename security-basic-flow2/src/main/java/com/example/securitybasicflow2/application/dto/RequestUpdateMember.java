package com.example.securitybasicflow2.application.dto;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Data
public class RequestUpdateMember {
    @NotEmpty(message = "email mat not be empty")
    private String name;
    @NotEmpty(message = "email mat not be empty")
    private String password;
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
    public RequestUpdateMember(String name, String password, MemberEntity.Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
