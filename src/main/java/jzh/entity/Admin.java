package jzh.entity;

import lombok.Data;

@Data
public class Admin {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String createTime;
    private String updateTime;
}