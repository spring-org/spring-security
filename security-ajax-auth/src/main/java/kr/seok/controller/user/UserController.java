package kr.seok.controller.user;


import kr.seok.domain.Account;
import kr.seok.domain.AccountDto;
import kr.seok.service.UserService;
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
//
//	@Autowired
//	private RoleRepository roleRepository;

	@GetMapping(value="/users")
	public String createUser() throws Exception {
		return "user/login/register";
	}

	@PostMapping(value="/users")
	public String createUser(AccountDto accountDto) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		/* Dto to Entity */
		Account account = modelMapper.map(accountDto, Account.class);
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

		userService.createUser(account);

		return "redirect:/";
	}

	@GetMapping(value="/mypage")
	public String myPage(
//			@AuthenticationPrincipal Account account, Authentication authentication, Principal principal
	) throws Exception {
		return "user/mypage";
	}

//	@GetMapping("/order")
//	public String order(){
//		userService.order();
//		return "user/mypage";
//	}

}
