package kr.seok.security.config;

import kr.seok.security.factory.UrlResourcesMapFactoryBean;
import kr.seok.security.form.common.FormAuthenticationDetailsSource;
import kr.seok.security.form.handler.FormAccessDeniedHandler;
import kr.seok.security.form.provider.FormAuthenticationProvider;
import kr.seok.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import kr.seok.security.service.SecurityResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* 인증 처리 중 추가 요청 파라미터를 저장하는 클래스 */
    @Autowired
    private FormAuthenticationDetailsSource formAuthenticationDetailsSource;

    @Qualifier("formAuthenticationSuccessHandler")
    @Autowired
    private AuthenticationSuccessHandler formAuthenticationSuccessHandler;

    @Qualifier("formAuthenticationFailureHandler")
    @Autowired
    private AuthenticationFailureHandler formAuthenticationFailureHandler;

    @Autowired
    private SecurityResourceService securityResourceService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FormAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /* static resource security 에서 제외 처리 */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")                                  /* form 태그의 action url */
                .authenticationDetailsSource(formAuthenticationDetailsSource)       /* request의 상세 값을 Details로 추가 하기 위한 작업 */
                .successHandler(formAuthenticationSuccessHandler)                   /* 사용자 정의 Success Handler */
                .failureHandler(formAuthenticationFailureHandler)
                .permitAll()
        ;
        http
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());

        /* FilterSecurityInterceptor 사용자 정의 */
        http
                .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);

        http.csrf().disable();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {

        FormAccessDeniedHandler formAccessDeniedHandler = new FormAccessDeniedHandler();
        formAccessDeniedHandler.setErrorPage("/denied");

        return formAccessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                /* 사용자 정의된 Provider 처리 */
                .authenticationProvider(authenticationProvider());
    }

    /* Custom FilterSecurityInterceptor 를 Filter에 추가하기 위한 Bean */
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /* DB 기반으로 URL Resource, 권한을 관리하기 위한 FilterInvocationSecurityMetadataSource 구현체 */
    @Bean
    public UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesMapFactoryBean().getObject(), securityResourceService);
    }

    private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {
        UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
        urlResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
        return urlResourcesMapFactoryBean;
    }

    /* 인가 처리를 하기 위한 사용자 정의 FilterSecurityInterceptor 구현체 */
    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
        return filterSecurityInterceptor;
    }

    /* 인가 하나의 투표 이상을 선택시 승인하는 방식의 클래스 */
    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        return Arrays.asList(new RoleVoter());
    }

}
