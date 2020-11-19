package kr.seok.security.filter;

import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 권한이 없어 인가처리가 필요하지 않은 resource에 대해서 permit All 처리하는 Filter
 */
public class PermitAllFilter extends FilterSecurityInterceptor {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
    private boolean observeOncePerRequest = true;

    public List<RequestMatcher> permitAllRequestMatcher = new ArrayList<>();

    public PermitAllFilter(String... permitAllResources) {
        for(String resource : permitAllResources) {
            permitAllRequestMatcher.add(new AntPathRequestMatcher(resource));
        }
    }

    /**
     *
     * @param object FilterInvocation (사용자 정보를 가져올 수 있음)
     * @return
     */
    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {
        boolean permitAll = false;
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for(RequestMatcher requestMatcher : permitAllRequestMatcher) {
            /* 허용한 resource */
            if(requestMatcher.matches(request)) {
                permitAll = true;
                break;
            }
        }
        /* 해당 경로가 permitAll에 해당하는 경로인 경우 return null; 처리 */
        if(permitAll) {
            return null;
        }
        /* 사용자 정의로 작성된 resource 경로 처리 후 상위 클래스로 이동하여 처리 */
        return super.beforeInvocation(object);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        if ((fi.getRequest() != null)
                && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                && observeOncePerRequest) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }
        else {
            // first time this request being called, so perform security checking
            if (fi.getRequest() != null && observeOncePerRequest) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            }
            finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }
    }
}
