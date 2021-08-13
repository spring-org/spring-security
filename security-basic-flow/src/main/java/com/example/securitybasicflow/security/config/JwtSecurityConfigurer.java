package com.example.securitybasicflow.security.config;

import com.example.securitybasicflow.security.filter.JwtLoginProcessingFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@Order(1)
public class JwtSecurityConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, JwtSecurityConfigurer<H>, JwtLoginProcessingFilter> {

    private AuthenticationManager authenticationManager;

    public JwtSecurityConfigurer(AuthenticationManager authenticationManager) {
        super(new JwtLoginProcessingFilter(authenticationManager), "/auth/login");
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, HttpMethod.POST.name());
    }

    @Override
    public void configure(H http) {

        if (authenticationManager == null) {
            authenticationManager = http.getSharedObject(AuthenticationManager.class);
        }

        getAuthenticationFilter().setAuthenticationManager(authenticationManager);

        http.setSharedObject(JwtLoginProcessingFilter.class, getAuthenticationFilter());
        http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
