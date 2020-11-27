package kr.seok.config;

import kr.seok.domain.repository.AccessIpRepository;
import kr.seok.domain.repository.ResourcesRepository;
import kr.seok.security.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityResource 를 Bean으로 관리하기 위한 Config
 */
@Configuration
public class AppConfig {
    @Bean
    public SecurityResourceService securityResourceService(ResourcesRepository resourcesRepository, AccessIpRepository accessIpRepository) {
        return new SecurityResourceService(resourcesRepository, accessIpRepository);
    }
}
