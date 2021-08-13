package com.example.securitybasicflow.application.service;

import com.example.securitybasicflow.application.domain.MemberEntity;
import com.example.securitybasicflow.application.dto.MemberDto;
import com.example.securitybasicflow.application.repository.MemberRepository;
import com.example.securitybasicflow.security.domain.Role;
import com.example.securitybasicflow.security.domain.RoleType;
import com.example.securitybasicflow.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto saveMember(MemberDto memberDto) {
        Role userRole = roleRepository.save(
                Role.builder().type(RoleType.ROLE_USER).build());

        MemberEntity memberEntity = MemberDto.toEntity(memberDto);
        memberEntity
                .grantRole(userRole)
                .encode(passwordEncoder.encode(memberDto.getPassword()));

        MemberEntity savedMember = memberRepository.save(memberEntity);

        return MemberDto.toDto(savedMember);
    }

    @Override
    public MemberDto updateMember(Long id, MemberDto updateToDto) {

        if (updateToDto.isEmptyRole()) {
            Role role = getRole(updateToDto.findFirst());

            MemberEntity updatedMember = getMemberEntity(id)
                    .update(updateToDto.getUsername(), updateToDto.getPassword())
                    .grantRole(role);

            return MemberDto.toDto(updatedMember);
        }

        MemberEntity updatedMember = getMemberEntity(id)
                .update(updateToDto.getUsername(), updateToDto.getPassword());

        return MemberDto.toDto(updatedMember);
    }

    private Role getRole(RoleType type) {
        return roleRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("Role 정보가 존재하지 않음"));
    }

    private MemberEntity getMemberEntity(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않음"));
    }
}
