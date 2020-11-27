package kr.seok.security.ajax.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.seok.domain.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    /* 인증에 성공한 인증 객체를 얻을 수 있음 */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Account account = (Account) authentication.getPrincipal();

        log.info("onAuthenticationSuccess : status {}", HttpStatus.OK.value());
        log.info("onAuthenticationSuccess : contentType {}", MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        /* account 타입으로 변환하여 client 에게 반환 */
        objectMapper.writeValue(response.getWriter(), account);
    }
}
