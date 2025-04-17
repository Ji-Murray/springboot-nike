package jzh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除session中的用户信息
        session.removeAttribute("user");
        // 重定向到首页
        return "redirect:/shouye";
    }
} 