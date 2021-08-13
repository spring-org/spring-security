package com.example.securitybasicflow.application.dto;

import com.example.securitybasicflow.security.dto.RoleDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class ResMember {

    private Long id;
    private String email;
    private String username;
    private Set<RoleDto> role;

    @Builder
    public ResMember(Long id, String email, String username, Set<RoleDto> role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public static ResMember toRes(MemberDto memberDto) {
        return ResMember.builder()
                .id(memberDto.getId())
                .email(memberDto.getEmail())
                .username(memberDto.getUsername())
                .role(memberDto.getRoles())
                .build();
    }
}
