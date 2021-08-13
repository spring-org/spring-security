package com.example.securitybasicflow.security.filter;

import com.example.securitybasicflow.application.dto.ReqLoginMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    public JwtLoginProcessingFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/auth/login", HttpMethod.POST.name()), authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("[Jwt Login Processing Filter] : START");
        ReqLoginMember reqLoginMember = mapper.readValue(request.getInputStream(), ReqLoginMember.class);

        if (!reqLoginMember.isValid()) {
            throw new IllegalArgumentException("값이 존재하지 않습니다.");
        }

        JwtAuthenticationToken authenticationToken =
                new JwtAuthenticationToken(reqLoginMember.getEmail(), reqLoginMember.getPassword());

        log.info("[Jwt Login Processing Filter] : END");
        return getAuthenticationManager().authenticate(authenticationToken);
    }

}
