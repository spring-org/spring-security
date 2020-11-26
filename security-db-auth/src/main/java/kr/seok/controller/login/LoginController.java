package kr.seok.controller.login;

import kr.seok.domain.entity.Account;
import kr.seok.security.ajax.token.AjaxAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Slf4j
@Controller
public class LoginController {
    /* Ajax 로그인 요청 방식은 POST로 받아야 함*/
    @RequestMapping(value = {"/login", "/api/login"})
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception
            , Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request, HttpServletResponse response
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, null);
        }
        return "redirect:/login";
    }

    @GetMapping(value = {"/denied", "/api/denied"})
    public String accessDenied(
            @RequestParam(value = "exception", required = false) String exception,
            Principal principal,
            Model model
    ) {
        Account account = null;

        if(principal instanceof UsernamePasswordAuthenticationToken) {
            account = (Account) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        } else if(principal instanceof AjaxAuthenticationToken) {
            account = (Account) ((AjaxAuthenticationToken) principal).getPrincipal();
        }

        log.info("Exception : {}", exception);
        assert account != null;
        model.addAttribute("username", account.getUsername());
        model.addAttribute("exception", exception);

        return "user/login/denied";
    }
}
