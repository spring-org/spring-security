package kr.seok.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    /* 인증 실패 처리 Handler */

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
    ) throws IOException, ServletException {

        /* Failure Handler */
        String errorMsg = "Invalid Username or Password";

        if(exception instanceof BadCredentialsException) {
            errorMsg = "Invalid Username or Password";
        } else if(exception instanceof InsufficientAuthenticationException) {
            errorMsg = "Invalid Secret Key";
        }

        setDefaultFailureUrl("/login?error=true&exception=" + errorMsg);

        super.onAuthenticationFailure(request, response, exception);
    }
}
