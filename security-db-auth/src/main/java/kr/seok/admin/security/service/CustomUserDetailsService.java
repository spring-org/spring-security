package kr.seok.admin.security.service;

import kr.seok.admin.domain.entity.Account;
import kr.seok.admin.domain.entity.Role;
import kr.seok.admin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userRepository.findByUsername(username);
        log.info("CustomUserDetailsService: {}", account);
        if(account == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        Set<String> userRoles = account.getUserRoles()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        List<GrantedAuthority> roles = userRoles
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new AccountContext(account, roles);
    }

}
