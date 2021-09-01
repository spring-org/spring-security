package com.example.securitybasicflow2.application.dto;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@JsonTypeName("member")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = NAME)
@JsonInclude(NON_NULL)
public class ResponseMember {
    private final Long memberId;
    private final String email;
    private final String password;
    private final String name;
    private final MemberEntity.Role role;

    public ResponseMember(MemberEntity member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.name = member.getName();
        this.role = member.getRole();
    }

    public static ResponseMember toResponse(MemberEntity member) {
        return new ResponseMember(member);
    }
}
