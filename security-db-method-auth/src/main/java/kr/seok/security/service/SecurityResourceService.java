package kr.seok.security.service;


import kr.seok.domain.entity.Resources;
import kr.seok.domain.repository.ResourcesRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SecurityResourceService {

    private final ResourcesRepository resourcesRepository;

    public SecurityResourceService(ResourcesRepository resourceRepository) {
        this.resourcesRepository = resourceRepository;
    }

    public LinkedHashMap<String, List<ConfigAttribute>> getMethodResourceList() {
        LinkedHashMap<String, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resources = resourcesRepository.findAllMethodResources();
        /* resource에 대해 매핑되는 role 가져오기 */
        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            /* */
            resource.getRoleSet()
                    .forEach(role -> configAttributes.add(new SecurityConfig(role.getRoleName())));
            result.put(resource.getResourceName(), configAttributes);
        });
        return result;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resources = resourcesRepository.findAllResources();
        /* resource에 대해 매핑되는 role 가져오기 */
        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            resource.getRoleSet().forEach(role ->
                    configAttributes.add(new SecurityConfig(role.getRoleName())));
            /* resource: roles == 1: N 으로 resource 하나당 여러 role을 매핑 처리 */
            result.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributes);
        });
        return result;
    }

}
