package kr.seok.admin.security.ajax.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.seok.admin.domain.dto.AccountDto;
import kr.seok.admin.security.ajax.token.AjaxAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  비동기 로그인 프로세스 필터
 *  1. AbstractAuthenticationProcessingFilter 를 상속받아 생성
 *  2. new AntPathRequestMatcher("/api/login")를 super 클래스의 인자로 전달하여 해당 url에 대해서 필터가 실행되도록 함
 *  3. boolean isAjax(request) Ajax 방식인지 확인
 *  4. objectMapper.readValue(request.getReader(), AccountDto.class) json 방식의 요청을 객체로 추출
 *
 */
@Slf4j
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException, IOException {

        log.info("attemptAuthentication: 시작");
        if(!isAjax(request)) {
            throw new IllegalStateException("Ajax 요청 방식이 아님");
        }

        /* json 타입의 데이터를 AccountDto 객체로 변롼 */
        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
        if(StringUtils.isEmpty(accountDto.getUsername()) || StringUtils.isEmpty(accountDto.getPassword())) {
            throw new IllegalArgumentException("사용자명 또는 비밀번호가 존재하지 않습니다.");
        }

        /* Ajax 방식으로 들어온 request의 인증 정보로 Token을 생성 */
        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());
        log.info("attemptAuthentication: 인증 토큰 정상 생성");
        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isAjax(HttpServletRequest request) {
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            log.info("attemptAuthentication: Ajax 요청 방식 확인");
            return true;
        }
        return false;
    }
}
