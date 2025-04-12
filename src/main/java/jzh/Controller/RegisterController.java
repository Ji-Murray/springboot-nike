package jzh.Controller;

import jzh.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam String username,
                          @RequestParam String password) {
        return userService.registerUser(username, password);
    }
} 