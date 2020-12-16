package kr.seok.security.voter;

import kr.seok.security.service.SecurityResourceService;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;

/**
 * IP에 대한 접근 결정 관리자
 */
public class IpAddressVoter implements AccessDecisionVoter<Object> {

    private SecurityResourceService securityResourceService;

    public IpAddressVoter(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        /* 사용자의 IP 정보를 얻기 위해 WebAuthenticationDetails로 캐스팅 하여 사용 */
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();

        /* DB에 저장된 IP와 일치한 것이 있는지 확인 */
//        List<String> accessIpList = securityResourceService.getAccessIpList();

        int result = ACCESS_DENIED;

//        for(String ipAddress : accessIpList){
//            if(remoteAddress.equals(ipAddress)){
//                /* IP에 대해서만 통과 하고 그 다음 권한이 존재하는지는 다른 Voter가 판단할 수 있도록 Abstain 처리 */
//                return ACCESS_ABSTAIN;
//            }
//        }

        /* IP가 허용되지 않은 경우 접근할 수 없도록 예외처리 */
        if(result == ACCESS_DENIED){
            throw new AccessDeniedException("Invalid IpAddress");
        }

        return result;
    }
}
