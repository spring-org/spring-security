package com.example.securitybasicflow2.application.domain;

import com.example.securitybasicflow2.application.dto.RequestUpdateMember;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TB_MEMBER")
@EqualsAndHashCode(of = {"id", "email"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID", nullable = false)
    private Long id;

    private String email;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public MemberEntity(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public MemberEntity grantRole(Role role) {
        this.role = role;
        return this;
    }

    public MemberEntity update(RequestUpdateMember updateMember) {

        if (updateMember.isName())
            this.name = updateMember.getName();
        if (updateMember.isPassword())
            this.password = updateMember.getPassword();
        if (updateMember.isRole())
            this.role = updateMember.getRole();

        return this;
    }

    @Getter
    public
    enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }
}
