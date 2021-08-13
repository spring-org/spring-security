package com.example.securitybasicflow.application.dto;

import com.example.securitybasicflow.application.domain.MemberEntity;
import com.example.securitybasicflow.security.domain.RoleType;
import com.example.securitybasicflow.security.dto.RoleDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
public class MemberDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private Set<RoleDto> roles;

    @Builder
    public MemberDto(Long id, String email, String username, String password, Set<RoleDto> roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public RoleType findFirst() {
        return roles.stream()
                .map(RoleDto::getType)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("정보가 존재하지 않습니다."));
    }

    public boolean isEmptyRole() {
        return roles.isEmpty();
    }

    public static MemberEntity toEntity(MemberDto member) {
        return MemberEntity.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .password(member.getPassword())
                .build();
    }

    public static MemberDto toDto(MemberEntity savedMember) {
        return MemberDto.builder()
                .id(savedMember.getId())
                .email(savedMember.getEmail())
                .username(savedMember.getUsername())
                .roles(RoleDto.toDto(savedMember.getRoles()))
                .build();
    }

    public static MemberDto saveToDto(ReqSaveMember member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .password(member.getPassword())
                .build();
    }

    public static MemberDto updateToDto(ReqUpdateMember member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .roles(Set.of(RoleDto.builder().type(member.getRole()).build()))
                .build();
    }
}
