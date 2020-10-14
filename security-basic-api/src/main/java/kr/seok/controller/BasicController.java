package kr.seok.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class BasicController {

    @GetMapping("/")
    public String index(HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication sessionContext = context.getAuthentication();

        return "Hello World";
    }

    @GetMapping("/thread")
    public String thread() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        /* ThreaLocal 모드에서는 인증 객체가 null, InheritableThreadLocal 모드의 경우 Thread간 인증 객체가 공유 가능 한 것을 확인 */
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                    }
                }
        ).start();
        return "thread";
    }

    @PostMapping("/")
    public String csrf() {
        return "Home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            ModelMap model
    ) {

        model.put("error", error);
        model.put("exception", exception);

        return "login";
    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin/pay")
    public String adminPay() {
        return "admin";
    }

    @GetMapping("/admin/**")
    public String adminAll() {
        return "sys";
    }
}
