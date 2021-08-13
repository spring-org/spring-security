package com.example.securitybasicflow.security.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Table(name = "roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Builder
    public Role(RoleType type) {
        this.type = type;
    }
}
