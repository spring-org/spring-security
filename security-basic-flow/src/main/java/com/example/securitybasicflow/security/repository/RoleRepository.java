package com.example.securitybasicflow.security.repository;

import com.example.securitybasicflow.security.domain.Role;
import com.example.securitybasicflow.security.domain.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByType(RoleType type);
}
