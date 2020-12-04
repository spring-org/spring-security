package kr.seok.security.config;

import kr.seok.security.factory.MethodResourceFactoryBean;
import kr.seok.security.service.SecurityResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/* 설정 파일이므로 @Configuration 추가 */
@Configuration
/*
 * Method 보안을 활성화 하기 위한 설정
 *  4가지 방식 중에 하나는 필수로 설정 되어 있어야 함
 *  1. boolean hasCustom = customMethodSecurityMetadataSource != null;
 *  2. boolean isPrePostEnabled = prePostEnabled();
 *  3. boolean isSecuredEnabled = securedEnabled();
 *  4. boolean isJsr250Enabled = jsr250Enabled();
 */
@EnableGlobalMethodSecurity
/* Method 보안을 위한 인가처리 메서드를 사용하기 위해 상속 */
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private SecurityResourceService securityResourceService;
    /* Custom 인가처리를 Method 보안으로 설정하기 위한 메서드 */
    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        /* 시큐리티에서 이미 제공하고 있는 Map 기반 MethodSecurityMetadataSource 클래스 -> Bean으로 처리 */
        return mapBasedMethodSecurityMetadataSource();
    }

    /* 빈으로 등록된 Map 기반 Method MetadataSource */
    @Bean
    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
        /* 기본 생성자가 아니라 DB로 부터 가져온 ResourceMap을 전달 */
        return new MapBasedMethodSecurityMetadataSource(methodResourcesMapFactoryBean().getObject());
    }

    /* DB로부터 가져오는 자원의 권한정보인 resourceMap */
    @Bean
    public MethodResourceFactoryBean methodResourcesMapFactoryBean() {

        MethodResourceFactoryBean methodResourceFactoryBean = new MethodResourceFactoryBean();
        methodResourceFactoryBean.setSecurityResourceService(securityResourceService);

        return methodResourceFactoryBean;
    }
}
