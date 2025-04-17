create table admin
(
    id          bigint auto_increment
        primary key,
    username    varchar(50)  not null comment '用户名',
    password    varchar(32)  not null comment '密码（MD5加密）',
    name        varchar(50)  null comment '姓名',
    email       varchar(100) null comment '邮箱',
    phone       varchar(20)  null comment '电话',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null comment '更新时间',
    constraint uk_username
        unique (username)
)
    comment '管理员表';

INSERT INTO nike.admin (id, username, password, name, email, phone, create_time, update_time) VALUES (1, 'admin', '123456', '系统管理员', null, null, '2025-04-15 20:42:45', '2025-04-15 20:42:45');
