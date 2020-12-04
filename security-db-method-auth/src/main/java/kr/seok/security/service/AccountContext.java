package kr.seok.security.service;

import kr.seok.domain.entity.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class AccountContext extends User {

    @Getter
    private Account account;

    public AccountContext(Account account, List<GrantedAuthority> roles) {
        super(account.getUsername(), account.getPassword(), roles);
        this.account = account;
    }

}
