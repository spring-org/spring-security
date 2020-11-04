package kr.seok.admin.controller.user;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

	@GetMapping(value="/messages")
	public String messages() {
		return "user/messages";
	}

	@PostMapping(value = {"/api/messages"})
	@ResponseBody
	public ResponseEntity<?> apiMessage() {
		return ResponseEntity.ok().body("ok");
	}
}
