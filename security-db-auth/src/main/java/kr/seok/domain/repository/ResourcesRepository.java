package kr.seok.domain.repository;


import kr.seok.domain.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {
    Resources findByResourceNameAndHttpMethod(String resourceName, String httpMethod);
    /* 구체적인 경로가 먼저오고 그것 보다 큰 범위의 경로가 뒤에 설정되기 위해 orderNum desc 처리 */
    @Query("select r from Resources r join fetch r.roleSet where r.resourceType = 'url' order by r.orderNum desc")
    List<Resources> findAllResources();
}
