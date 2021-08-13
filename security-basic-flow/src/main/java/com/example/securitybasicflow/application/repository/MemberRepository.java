package com.example.securitybasicflow.application.repository;

import com.example.securitybasicflow.application.domain.MemberEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
}
