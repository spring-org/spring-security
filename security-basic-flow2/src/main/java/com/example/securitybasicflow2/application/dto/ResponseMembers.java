package com.example.securitybasicflow2.application.dto;

import com.example.securitybasicflow2.application.domain.MemberEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@JsonTypeName(value = "member")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = NAME)
@JsonInclude(NON_NULL)
public class ResponseMembers {

    private final List<ResponseMember> members;
    private final int membersSize;

    private ResponseMembers(List<ResponseMember> members, int membersSize) {
        this.members = members;
        this.membersSize = membersSize;
    }

    public static ResponseMembers of(List<MemberEntity> members) {
        List<ResponseMember> responseMembers = members.stream()
                .map(ResponseMember::toResponse)
                .collect(Collectors.toList());
        return new ResponseMembers(responseMembers, responseMembers.size());
    }
}
