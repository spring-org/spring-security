package kr.seok.security.service;

import kr.seok.domain.entity.Resources;
import kr.seok.domain.repository.AccessIpRepository;
import kr.seok.domain.repository.ResourcesRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityResourceService {

    private ResourcesRepository resourcesRepository;
    private AccessIpRepository accessIpRepository;

    public SecurityResourceService(ResourcesRepository resourceRepository, AccessIpRepository accessIpRepository) {
        this.resourcesRepository = resourceRepository;
        this.accessIpRepository = accessIpRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resources = resourcesRepository.findAllResources();
        /* resource에 대해 매핑되는 role 가져오기 */
        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            resource.getRoleSet().forEach(role -> {
                configAttributes.add(new SecurityConfig(role.getRoleName()));
                result.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributes);
            });
        });
        return result;
    }

    public List<String> getAccessIpList() {
        return accessIpRepository.findAll()
                .stream()
                .map(accessIp -> accessIp.getIpAddress())
                .collect(Collectors.toList());
    }
}
