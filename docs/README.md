# Table of Contents

## 1. 강의 소개

[intro](/docs/lecture/1.intro.md)

## 2. 스프링 시큐리티 기본 API & Filter 이해

[security-basic](/docs/lecture/2.security-basic.md)

## 3. 시큐리티 주요 아키텍처 이해 

[security-architecture](/docs/lecture/3.security-core.md)

3. 시큐리티 주요 아키텍처 이해
    1) Proxy
    2) 필터 초기화와 다중 보안 설정
    3) Authentication
    4) SecurityContextHolder, SecurityContext
    5) SecurityContextPersistenceFilter
    6) Authentication
        (1) AuthenticationManager Basic
        (2) AuthenticationManager Advance
    7) AuthenticationProvider
    8) Authorization, FilterSecurityInterceptor
    9) AccessDecisionManager, AccessDecisionVoter
    10) 스프링 시큐리티 필터 및 아키텍처 정리

## 4. 인증(Authentication) 프로세스 구현

[security-authentication](/docs/lecture/4.authentication.md)

1. 실전 프로젝트 구성
2. 메뉴 권한 및 WebIgnore 설정
3. Form 인증
    1) User 등록 / PasswordEncoder
    2) CustomUserDetailsService 구현
    3) CustomAuthenticationProvider 구현
    4) CustomLoginFormPage
    5) 로그아웃 및 화면 보안 처리
    6) WebAuthenticationDetails, AuthenticationDetailsSource
    7) CustomAuthenticationSuccessHandler
    8) CustomAuthenticationFailureHandler
    9) AccessDeniedHandler
    10) CSRF, csrfFilter
    11) 인증 사용자 정보 구하기
    
4. Ajax 인증
    1) 흐름 및 개요
    2) AjaxAuthenticationFilter
    3) AjaxAuthenticationProvider
    4) AjaxAuthenticationSuccessHandler
    5) AjaxAuthenticationFailureHandler
    6) AjaxLoginUrlAuthenticationEntryPoint & AjaxAccessDeniedHandler
    7) DSL로 Config 설정하기
    8) 로그인 Ajax 구현 & CSRF

## 5. 인가(Authorization) 프로세스 구현 - DB 연동

### Assert 비즈니스 로직에서 사용하지 않는 이유
### SessionManagementFilter
### Voter
