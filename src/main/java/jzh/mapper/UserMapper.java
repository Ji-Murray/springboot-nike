package jzh.mapper;

import jzh.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    int existsByUsername(@Param("username") String username);

    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user")
    java.util.List<User> findAll();
} 