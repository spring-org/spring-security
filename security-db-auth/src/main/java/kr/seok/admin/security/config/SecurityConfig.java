package kr.seok.admin.security.config;

import kr.seok.admin.security.form.handler.CustomAccessDeniedHandler;
import kr.seok.admin.security.form.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* 인증 처리 중 추가 요청 파라미터를 저장하는 클래스 */
    @Autowired
    private AuthenticationDetailsSource authenticationDetailsSource;

    @Qualifier("customAuthenticationSuccessHandler")
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Qualifier("customAuthenticationFailureHandler")
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
        customAccessDeniedHandler.setErrorPage("/denied");

        return customAccessDeniedHandler;
    }

    private String[] permitAllResources = {"/", "/users", "/login", "/user/login/**", "/h2-console"};

    /* static resource security 에서 제외 처리 */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Authentication API*/
        http
                .authorizeRequests()
                .antMatchers(permitAllResources).permitAll()
                .anyRequest().authenticated()
                ;
        /* FormLogin API */
        http
                .formLogin()
                .loginPage("/login")
                /* form 태그의 action url */
                .loginProcessingUrl("/login_proc")
//                /* 로그인 성공 후 redirect page */
//                .defaultSuccessUrl("/")
                /* request의 상세 값을 Details로 추가 하기 위한 작업 */
                .authenticationDetailsSource(authenticationDetailsSource)
                /* 사용자 정의 Success Handler */
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
        ;
        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                /* 사용자 정의된 Provider 처리 */
                .authenticationProvider(authenticationProvider());
    }
}
