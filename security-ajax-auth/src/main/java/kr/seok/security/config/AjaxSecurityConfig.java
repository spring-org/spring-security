package kr.seok.security.config;

import kr.seok.security.filter.AjaxLoginProcessingFilter;
import kr.seok.security.provider.AjaxAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /* Custom 으로 만들어진 Ajax 용 Filter 작성 */
    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
        ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        return ajaxLoginProcessingFilter;
    }

    /* 인증 처리를 수행하는 Provider */
    @Bean
    public AuthenticationProvider ajaxAuthenticationProvider() {
        return new AjaxAuthenticationProvider();
    }

    /* AjaxAuthenticationFilter 를 FilterChain에 등록하기 위한 AuthenticationManager 정의 */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* 요청 URL만 AjaxAuthenticationFilter가 적용되도록 설정 */
                .antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().authenticated()
                ;
        http
                /* 필터 추가 form 인증 필터 전에 수행 */
                .addFilterBefore(
                        ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class
                );
        //http.csrf().disable()
                ;
    }
}
