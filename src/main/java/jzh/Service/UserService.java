package jzh.Service;

import jzh.mapper.UserMapper;
import jzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    // 注册用户
    public String registerUser(String username, String password) {
        if (userMapper.existsByUsername(username) > 0) {
            return "用户名已存在";
        }

        User user = new User(username, password);
        userMapper.insert(user);
        return "注册成功";
    }

    // 用户登录
    public String loginUser(String username, String password) {
        try {
            logger.info("尝试登录用户: {}", username);
            User user = userMapper.findByUsername(username);
            if (user == null) {
                logger.warn("用户不存在: {}", username);
                return "用户名不存在";
            }

            if (!user.getPassword().equals(password)) {
                logger.warn("密码错误: {}", username);
                return "密码错误";
            }

            logger.info("登录成功: {}", username);
            return "登录成功";
        } catch (Exception e) {
            logger.error("登录过程中发生错误", e);
            return "登录失败: " + e.getMessage();
        }
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
}
