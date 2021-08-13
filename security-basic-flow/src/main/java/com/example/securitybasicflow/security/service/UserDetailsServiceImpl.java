package com.example.securitybasicflow.security.service;

import com.example.securitybasicflow.application.domain.MemberEntity;
import com.example.securitybasicflow.application.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[UserDetailsService] : START");
        MemberEntity memberEntity = memberRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));
        log.info("[UserDetailsService] : END");
        return UserDetailsImpl.of(memberEntity);
    }
}
