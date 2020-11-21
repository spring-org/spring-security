package kr.seok.controller.admin;


import kr.seok.domain.dto.AccountDto;
import kr.seok.domain.entity.Account;
import kr.seok.domain.entity.Role;
import kr.seok.service.RoleService;
import kr.seok.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserManagerController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 사용자 정보 리스트 페이지 조회
	 * @param model 사용자 정보 리스트
	 * @return 사용자 정보 리스트 페이지 이동
	 */
	@GetMapping(value="/admin/accounts")
	public String getUsers(Model model) {

		List<Account> accounts = userService.getUsers();
		model.addAttribute("accounts", accounts);

		return "admin/user/list";
	}

	/**
	 * 사용자 정보 수정
	 * @param accountDto 사용자의 수정할 정보
	 * @return 사용자 정보 수정 후 상세 페이지로 이동
	 */
	@PostMapping(value="/admin/accounts")
	public String modifyUser(AccountDto accountDto) {

		userService.modifyUser(accountDto);

		return "redirect:/admin/accounts";
	}

	/**
	 * 사용자 정보 상세 조회
	 * @param id 사용자 키 값
	 * @param model 사용자 정보, 권한 설정 리스트
	 * @return 사용자 상세 페이지로 이동
	 */
	@GetMapping(value = "/admin/accounts/{id}")
	public String getUser(@PathVariable(value = "id") Long id, Model model) {

		AccountDto accountDto = userService.getUser(id);
		List<Role> roleList = roleService.getRoles();

		model.addAttribute("account", accountDto);
		model.addAttribute("roleList", roleList);

		return "admin/user/detail";
	}

	/**
	 * user 정보 삭제
	 * @param id 사용자 키 값
	 * @return 사용자 정보 삭제 후 페이지 리다이렉트
	 */
	@GetMapping(value = "/admin/accounts/delete/{id}")
	public String removeUser(@PathVariable(value = "id") Long id) {

		userService.deleteUser(id);

		return "redirect:/admin/users";
	}
}
