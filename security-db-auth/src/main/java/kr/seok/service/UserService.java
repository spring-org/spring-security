package kr.seok.service;

import kr.seok.domain.dto.AccountDto;
import kr.seok.domain.entity.Account;

import java.util.List;

public interface UserService {

    void createUser(Account account);

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();

    AccountDto getUser(Long id);

    void deleteUser(Long idx);

    void order();
}
