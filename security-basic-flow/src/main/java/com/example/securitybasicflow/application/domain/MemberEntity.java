package com.example.securitybasicflow.application.domain;

import com.example.securitybasicflow.security.domain.Role;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id", nullable = false)
    private Long id;

    private String email;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")

    )
    private Set<Role> roles = new HashSet<>();

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Builder
    public MemberEntity(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public MemberEntity update(String username, String password) {
        if(StringUtils.hasText(username)) {
            this.username = username;
        }
        if(StringUtils.hasText(password)) {
            this.password = password;
        }
        return this;
    }

    public MemberEntity grantRole(Role role) {
        roles.add(role);
        return this;
    }

    public MemberEntity revokeRole(Role role) {
        roles.remove(role);
        return this;
    }
    public MemberEntity encode(String password) {
        this.password = password;
        return this;
    }
}
