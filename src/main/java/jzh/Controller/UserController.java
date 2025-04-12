package jzh.Controller;

import jzh.Service.UserService;
import jzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shouye")
public class UserController {

    @Autowired
    private UserService userService;

    // 注册接口
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password) {
        return userService.registerUser(username, password);
    }

    // 登录接口
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        return userService.loginUser(username, password);
    }

    // 获取所有用户（测试用）
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}