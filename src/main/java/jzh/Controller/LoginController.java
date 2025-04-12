package jzh.Controller;

import jzh.Service.UserService;
import jzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session) {
        String result = userService.loginUser(username, password);
        if ("登录成功".equals(result)) {
            User user = userService.findByUsername(username);
            session.setAttribute("user", user);
        }
        return result;
    }
} 