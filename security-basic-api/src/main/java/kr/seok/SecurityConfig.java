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
                .anyRequest().authenticated();
        // 인증 정책
        http
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
                ;
    }
}


























