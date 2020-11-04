package kr.seok.admin.controller.user;


import kr.seok.admin.domain.dto.AccountDto;
import kr.seok.admin.domain.entity.Account;
import kr.seok.admin.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping(value="/users")
	public String createUser() {
		return "user/login/register";
	}

	@PostMapping(value="/users")
	public String createUser(AccountDto accountDto) {

		ModelMapper modelMapper = new ModelMapper();
		/* Dto to Entity */
		Account account = modelMapper.map(accountDto, Account.class);
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

		userService.createUser(account);

		return "redirect:/";
	}

	@GetMapping(value="/mypage")
	public String myPage() {
		return "user/mypage";
	}

}
