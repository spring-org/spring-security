package kr.seok.service.impl;

import kr.seok.domain.entity.RoleHierarchy;
import kr.seok.domain.repository.RoleHierarchyRepository;
import kr.seok.service.RoleHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


/**
 * 정의된 권한의 위계를 만들어 등록된 권한의 하위 레벨이 존재하는 경우, 하위 권한도 포함되도록 설정
 */
@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    @Autowired
    private RoleHierarchyRepository roleHierarchyRepository;

    /**
     * DB에 저장된 ROLE 정보를 {Child} > {Parent} 패턴으로 변형하여
     * 해당 프로젝트의 AccessVoter 가 사용자의 권한에 대한 인가 처리흫 할 수 있도록 하는 메서드
     *
     * <pre>
     * ROLE_ADMIN > ROLE_MANAGER
     * ROLE_MANAGER > ROLE_USER
     * </pre>
     * @return 권한의 위계를 만들어 반환
     */
    @Transactional
    @Override
    public String findAllHierarchy() {

        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuilder appendRoles = new StringBuilder();
        while (itr.hasNext()) {
            RoleHierarchy roleHierarchy = itr.next();
            if (roleHierarchy.getParentName() != null) {
                appendRoles.append(roleHierarchy.getParentName().getChildName());
                appendRoles.append(" > ");
                appendRoles.append(roleHierarchy.getChildName());
                appendRoles.append("\n");
            }
        }
        return appendRoles.toString();

    }
}
