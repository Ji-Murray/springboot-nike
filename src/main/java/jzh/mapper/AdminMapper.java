package jzh.mapper;

import jzh.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin findByUsername(String username);

    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin findById(Long id);

    @Insert("INSERT INTO admin (username, password, name, email, phone, create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{name}, #{email}, #{phone}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Admin admin);

    @Update("UPDATE admin SET password = #{password}, name = #{name}, " +
            "email = #{email}, phone = #{phone}, update_time = NOW() " +
            "WHERE id = #{id}")
    int update(Admin admin);

    @Delete("DELETE FROM admin WHERE id = #{id}")
    int deleteById(Long id);
}