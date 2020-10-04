package kr.seok.controller.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	private RoleRepository roleRepository;

//	@GetMapping(value="/users")
//	public String createUser() throws Exception {
//
//		return "user/login/register";
//	}

//	@PostMapping(value="/users")
//	public String createUser(AccountDto accountDto) throws Exception {
//
//		ModelMapper modelMapper = new ModelMapper();
//		Account account = modelMapper.map(accountDto, Account.class);
//		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//
//		userService.createUser(account);
//
//		return "redirect:/";
//	}

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
