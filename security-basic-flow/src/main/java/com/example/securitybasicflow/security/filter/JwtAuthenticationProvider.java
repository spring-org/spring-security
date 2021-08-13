package com.example.securitybasicflow.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("[JWT Authentication Provider] : START");
        String principal = (String) authentication.getPrincipal();
        String credentials = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal);

        if(!passwordEncoder.matches(credentials, userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호 문제");
        }

        log.info("[JWT Authentication Provider] : END");
        return new JwtAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
