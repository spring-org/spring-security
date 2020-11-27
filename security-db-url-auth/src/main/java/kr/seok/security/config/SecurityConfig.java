package kr.seok.security.config;

import kr.seok.security.factory.UrlResourcesMapFactoryBean;
import kr.seok.security.filter.PermitAllFilter;
import kr.seok.security.form.common.FormAuthenticationDetailsSource;
import kr.seok.security.form.handler.FormAccessDeniedHandler;
import kr.seok.security.form.provider.FormAuthenticationProvider;
import kr.seok.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import kr.seok.security.service.SecurityResourceService;
import kr.seok.security.voter.IpAddressVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
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

import java.util.ArrayList;
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

    private final String[] permitAllResources = {"/", "/login", "/user/login/**"};

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
                .addFilterBefore(permitAllFilter(), FilterSecurityInterceptor.class);
        http
                .csrf().disable();
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
    public PermitAllFilter permitAllFilter() throws Exception {
        /* 인가 처리 필터를 PermitAllFilter로 변경 */
        PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllResources);
        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        permitAllFilter.setAccessDecisionManager(affirmativeBased());
        permitAllFilter.setAuthenticationManager(authenticationManagerBean());
        return permitAllFilter;
    }

    /* 인가 하나의 투표 이상을 선택시 승인하는 방식의 클래스 */
    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    /**
     * 권한 인가 결정자 등록
     *  - 기본적인경우 사용자의 단일 권한에 대해 resource에 정의된 권한을 1:1 매핑하여 인가 처리 (RoleVoter)
     *  - 권한에 대한 위계가 존재하도록 구성하는 경우 사용자의 권한과 해당 프로그램에 정의된 권한위계를 비교하여 인가처리를 실시 (CustomRoleVoter)
     */
    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        /* Voter 심의 시 IP를 우선적으로 처리 필요 [순서 중요] */
        accessDecisionVoters.add(new IpAddressVoter(securityResourceService));
        accessDecisionVoters.add(roleVoter());
        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    /**
     * 기존 구현체
     * @return
     */
    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        return new RoleHierarchyImpl();
    }

}
