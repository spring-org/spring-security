package com.example.securitybasicflow2;

import com.example.securitybasicflow2.core.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityBasicFlow2ApplicationTests {

    @Test
    void contextLoads() {
    }

    @DisplayName("기본 스프링 시큐리티 필터 체인 확인하기")
    @Test
    void testCase1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SecurityConfig.class);

        FilterChainProxy bean = (FilterChainProxy) context.getBean("springSecurityFilterChain");
        List<SecurityFilterChain> filterChains = bean.getFilterChains();
        filterChains.get(0).getFilters().forEach(
                filter -> System.out.println("filter = " + filter)
        );

        assertThat(filterChains.size()).isOne();
    }
}
