package kr.seok.security.config;

import kr.seok.security.ajax.common.AjaxLoginAuthenticationEntryPoint;
import kr.seok.security.ajax.handler.AjaxAccessDeniedHandler;
import kr.seok.security.ajax.handler.AjaxAuthenticationFailureHandler;
import kr.seok.security.ajax.handler.AjaxAuthenticationSuccessHandler;
import kr.seok.security.ajax.provider.AjaxAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@Order(0)
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

    /* 인증 처리를 수행하는 Provider */
    @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider();
    }

    /* 인증 성공 시 처리 Handler */
    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    /* 인증 실패 시 처리 Handler */
    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    /* 인가 실패 시 처리 Handler */
    @Bean
    public AccessDeniedHandler ajaxAccessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

    /* AjaxAuthenticationFilter 를 FilterChain에 등록하기 위한 AuthenticationManager 정의 */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /* 인증 처리 Manager 등록 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* 요청 URL만 AjaxAuthenticationFilter가 적용되도록 설정 */
                .antMatcher("/api/**")
                .authorizeRequests()
                /* 비동기 통신으로 요청한 사용자의 접근을 허용 */
                .antMatchers("/api/messages").hasRole("USER")
                .antMatchers("/api/login").permitAll()
                /* 그 외 요청들에 대해서 인증 처리 필요 설정 */
                .anyRequest().authenticated();
                /* 예외 처리 */

        http    .exceptionHandling()
                /* 인증 처리가 되지 않은 사용자의 요청 정보를 임시 저장하는 기능 */
                .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
                /* 인가가 승인되지 않은 사용자의 접근 거부 처리 */
                .accessDeniedHandler(ajaxAccessDeniedHandler());

        ajaxConfigurer(http);
    }

    /* Custom DSL SecurityConfig */
    private void ajaxConfigurer(HttpSecurity http) throws Exception {
        http
                .apply(new AjaxLoginConfigurer<>())
                /* SuccessHandler */
                .successHandlerAjax(ajaxAuthenticationSuccessHandler())
                /* FailureHandler */
                .failureHandlerAjax(ajaxAuthenticationFailureHandler())
                /* AuthenticationManager */
                .setAuthenticationManager(authenticationManagerBean())
                /* 로그인 처리 URL */
                .loginPage("/api/login")
                .loginProcessingUrl("/api/login")
                ;
    }
}
