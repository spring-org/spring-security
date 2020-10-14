package kr.seok.security.common;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AuthenticationDetails 를 생성하는 Bean 클래스
 */
@Component
public class FormAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest , WebAuthenticationDetails> {
    /* */
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new FormWebAuthenticationDetails(context);
    }
}
