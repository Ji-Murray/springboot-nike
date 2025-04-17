create table `order`
(
    id          bigint auto_increment
        primary key,
    user_id     bigint                                not null,
    product_id  bigint                                not null,
    size        varchar(20)                           not null,
    quantity    int         default 1                 not null,
    price       decimal(10, 2)                        not null,
    status      varchar(20) default '待付款'          not null,
    create_time timestamp   default CURRENT_TIMESTAMP null,
    update_time timestamp   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint order_ibfk_1
        foreign key (user_id) references user (id),
    constraint order_ibfk_2
        foreign key (product_id) references product (id)
);

create index product_id
    on `order` (product_id);

create index user_id
    on `order` (user_id);

INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (1, 1, 5, 'S(US 40)', 1, 1.00, '待发货', '2025-04-15 13:03:28', '2025-04-17 17:45:40');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (2, 1, 6, 'XXL(US 56)', 1, 1.00, '已取消', '2025-04-15 18:23:20', '2025-04-15 18:57:52');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (3, 1, 6, 'XXL(US 56)', 1, 1.00, '已取消', '2025-04-15 18:23:48', '2025-04-15 18:57:58');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (4, 1, 6, 'XXL(US 56)', 1, 1.00, '已取消', '2025-04-15 18:24:10', '2025-04-15 18:58:08');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (5, 1, 1, 'M(US 44)', 1, 299.00, '已取消', '2025-04-15 19:04:15', '2025-04-15 20:16:25');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (6, 1, 8, 'M(US 44)', 1, 1.00, '已完成', '2025-04-15 19:05:00', '2025-04-15 20:16:56');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (7, 1, 6, 'M(US 44)', 1, 1.00, '已付款', '2025-04-15 19:05:25', '2025-04-15 19:05:39');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (8, 1, 5, 'M(US 44)', 1, 1.00, '已取消', '2025-04-16 21:40:19', '2025-04-17 18:03:00');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (9, 1, 5, 'S(US 40)', 1, 1.00, '已付款', '2025-04-16 22:30:06', '2025-04-16 22:30:13');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (10, 1, 1, 'S(US 40)', 1, 299.00, '已付款', '2025-04-17 18:02:40', '2025-04-17 18:02:47');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (11, 1, 1, 'XL(US 52)', 1, 299.00, '已取消', '2025-04-17 18:06:16', '2025-04-17 18:06:19');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (12, 1, 15, 'M(US 44)', 1, 599.00, '待付款', '2025-04-17 19:51:51', '2025-04-17 19:51:50');
INSERT INTO nike.`order` (id, user_id, product_id, size, quantity, price, status, create_time, update_time) VALUES (13, 1, 1, 'S(US 40)', 1, 299.00, '待付款', '2025-04-17 19:52:35', '2025-04-17 19:52:35');
