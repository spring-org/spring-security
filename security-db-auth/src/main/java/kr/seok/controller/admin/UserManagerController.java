package kr.seok.controller.admin;


import kr.seok.domain.dto.AccountDto;
import kr.seok.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserManagerController {

	@Autowired
	private UserService userService;

//	@Autowired
//	private RoleService roleService;

	@GetMapping(value="/admin/accounts")
	public String getUsers(Model model) throws Exception {

//		List<Account> accounts = userService.getUsers();
//		model.addAttribute("accounts", accounts);

		return "admin/user/list";
	}

	@PostMapping(value="/admin/accounts")
	public String modifyUser(AccountDto accountDto) throws Exception {

//		userService.modifyUser(accountDto);

		return "redirect:/admin/accounts";
	}

	@GetMapping(value = "/admin/accounts/{id}")
	public String getUser(@PathVariable(value = "id") Long id, Model model) {

//		AccountDto accountDto = userService.getUser(id);
//		List<Role> roleList = roleService.getRoles();

//		model.addAttribute("account", accountDto);
//		model.addAttribute("roleList", roleList);

		return "admin/user/detail";
	}

	@GetMapping(value = "/admin/accounts/delete/{id}")
	public String removeUser(@PathVariable(value = "id") Long id, Model model) {

//		userService.deleteUser(id);

		return "redirect:/admin/users";
	}
}
