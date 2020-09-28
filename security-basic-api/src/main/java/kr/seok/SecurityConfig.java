package kr.seok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인가 정책
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
        // 인증 정책
            .and()
                .formLogin()
//                .loginPage("/loginPage")
//                .defaultSuccessUrl("/")
//                .failureUrl("/login")
//                .usernameParameter("userId")
//                .passwordParameter("passwd")
//                .loginProcessingUrl("/login_proc")
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        System.out.println("authentication : " + authentication.getName());
//                        response.sendRedirect("/");
//                    }
//                })
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                        System.out.println("exception" + exception.getMessage());
//                        response.sendRedirect("/login");
//                    }
//                })
//                .permitAll()
//            .and()
//                .logout()
//                /* GET, POST 가능 */
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .addLogoutHandler(new LogoutHandler() {
//                    @Override
//                    public void logout(
//                            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//                        HttpSession session = request.getSession();
//                        session.invalidate();
//                    }
//                })
//                .logoutSuccessHandler(new LogoutSuccessHandler() {
//                    @Override
//                    public void onLogoutSuccess(
//                            HttpServletRequest request, HttpServletResponse response, Authentication authentication
//                    ) throws IOException, ServletException {
//                        response.sendRedirect("/login");
//                    }
//                })
//                /* 쿠키명 */
//                .deleteCookies("remember-me")
            .and()
                .rememberMe()
//                .rememberMeParameter("remember") // 기본 파라미터 명 -> "remember-me"
//                .tokenValiditySeconds(3600) // 만료시간 default 14일
//                .alwaysRemember(true) // remember me  기능이 활성화되지 않아도 항상 실행
                /* user 계정 확인 메서드 */
                .userDetailsService(userDetailsService)
            .and()
                .anonymous()
            .and()
                /* 세션 관리 */
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
//                .expiredUrl("/login")
                /* 위 API 와 함께 사용할 수 없음 */
//                .sessionManagement()
//                .invalidSessionUrl("/login")


            /* 사용자의 쿠키를 공격자의 쿠키로 인증처리 한 뒤 공격자가 해당 쿠키로 인증하는 세션 고정 공격 */
//            .and()
                /* 세션 고정 보호*/
//                .sessionManagement()
//                .sessionFixation()
                /* 세센 고정 보호를 사용하지 않는 경우 위와 같은 문제가 발생할 수 있음 */
//                 .none()
//                 .migrateSession()
//                 .newSession()
                /* 인증 처리 시 기존 세션 내용을 새로운 인증 세션으로 변경하는 방법 */
//                .changeSessionId()
//            .and()
//                .sessionManagement()
                /* 스프링 시큐리티가 항상 세션을 생성하는 정책 */
                // .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                /* 스프링 시큐리티가 생성하지 않지만 이미 존재하면 사용 */
                // .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                /* 스프링 시큐리티가 생성하지 않고 존재해도 사용하지 않음 */
                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /* 스프링 시큐리티가 필요 시 생성(기본값) */
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            ;
    }
}


























