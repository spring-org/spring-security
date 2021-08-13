package com.example.securitybasicflow.security.dto;

import com.example.securitybasicflow.security.domain.Role;
import com.example.securitybasicflow.security.domain.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleDto {

    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Builder
    public RoleDto(RoleType type) {
        this.type = type;
    }

    public static Set<RoleDto> toDto(Set<Role> roles) {
        return roles.stream()
                .map(role -> RoleDto.builder()
                        .type(role.getType())
                        .build())
                .collect(Collectors.toSet());
    }
}
