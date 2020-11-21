package kr.seok.security.init;

import kr.seok.domain.entity.Account;
import kr.seok.domain.entity.Resources;
import kr.seok.domain.entity.Role;
import kr.seok.domain.entity.RoleHierarchy;
import kr.seok.domain.repository.ResourcesRepository;
import kr.seok.domain.repository.RoleHierarchyRepository;
import kr.seok.domain.repository.RoleRepository;
import kr.seok.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private RoleHierarchyRepository roleHierarchyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private AccessIpRepository accessIpRepository;

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();

//        setupAccessIpData();

        alreadySetup = true;
    }

    private Set<Role> getUserRoles() {
        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자권한");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        return roles;
    }

    private Set<Role> getManagerRoles() {
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저권한");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(managerRole);
        return roles;
    }

    private Set<Role> getAdminRoles() {
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저권한");
        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자권한");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(managerRole);
        roles.add(userRole);
        return roles;
    }

    private void setupSecurityResources() {
        Set<Role> roles;
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저권한");
        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자권한");


        createUserIfNotFound("admin", "admin@admin.com", "pass", getAdminRoles());
        createUserIfNotFound("manager", "manager@gmail.com", "1234", getManagerRoles());
        createUserIfNotFound("user", "user@gmail.com", "1234", getUserRoles());

        roles = new HashSet<>();
        roles.add(adminRole);
        createResourceIfNotFound("/admin/**", "", roles, "url");

        roles = new HashSet<>();
        roles.add(managerRole);
        createResourceIfNotFound("/messages", "", getAdminRoles(), "url");

        roles = new HashSet<>();
        roles.add(userRole);
        createResourceIfNotFound("/mypage", "", getAdminRoles(), "url");

        roles = new HashSet<>();
        roles.add(adminRole);
        createResourceIfNotFound("/config", "", roles, "url");

//        createResourceIfNotFound("execution(public * io.security.corespringsecurity.aopsecurity.*Service.pointcut*(..))", "", roles, "pointcut");
        createRoleHierarchyIfNotFound(managerRole, adminRole);
        createRoleHierarchyIfNotFound(userRole, managerRole);
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Account createUserIfNotFound(final String userName, final String email, final String password, Set<Role> roleSet) {

        Account account = userRepository.findByUsername(userName);

        if (account == null) {
            account = Account.builder()
                    .username(userName)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .userRoles(roleSet)
                    .build();
        }
        return userRepository.save(account);
    }

    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, Set<Role> roleSet, String resourceType) {
        Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if (resources == null) {
            resources = Resources.builder()
                    .resourceName(resourceName)
                    .roleSet(roleSet)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
                    .orderNum(count.incrementAndGet())
                    .build();
        }
        return resourcesRepository.save(resources);
    }

    @Transactional
    public void createRoleHierarchyIfNotFound(Role childRole, Role parentRole) {

        RoleHierarchy roleHierarchy = roleHierarchyRepository.findByChildName(parentRole.getRoleName());
        if (roleHierarchy == null) {
            roleHierarchy = RoleHierarchy.builder()
                    .childName(parentRole.getRoleName())
                    .build();
        }
        RoleHierarchy parentRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);

        roleHierarchy = roleHierarchyRepository.findByChildName(childRole.getRoleName());
        if (roleHierarchy == null) {
            roleHierarchy = RoleHierarchy.builder()
                    .childName(childRole.getRoleName())
                    .build();
        }

        RoleHierarchy childRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);
        childRoleHierarchy.setParentName(parentRoleHierarchy);
    }

//    private void setupAccessIpData() {
//
//        AccessIp byIpAddress = accessIpRepository.findByIpAddress("0:0:0:0:0:0:0:1");
//        if (byIpAddress == null) {
//            AccessIp accessIp = AccessIp.builder()
//                    .ipAddress("0:0:0:0:0:0:0:1")
//                    .build();
//            accessIpRepository.save(accessIp);
//        }
//    }
}
