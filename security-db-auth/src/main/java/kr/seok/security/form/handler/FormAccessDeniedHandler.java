package kr.seok.security.form.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class FormAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException
    ) throws IOException {

        log.debug("예외 : {} ", accessDeniedException.getMessage());
        String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage();
        response.sendRedirect(deniedUrl);
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
