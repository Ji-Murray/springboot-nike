create table product
(
    id          bigint auto_increment
        primary key,
    name        varchar(255)   null,
    description varchar(255)   null,
    image_url   varchar(255)   null,
    price       decimal(38, 2) null,
    category    varchar(255)   null,
    recommended tinyint(1)     null,
    stock       int            null,
    update_time datetime(6)    null,
    create_time datetime(6)    null
);

INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (1, '新款球衣', '2024年最新款球衣', '/img/6c6dc7b0-bbb7-4de3-bc61-c599b9d9903b.jpg', 299.00, '新品', null, 3, '2025-04-17 16:46:25.000000', null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (4, '儿童球衣', '儿童专业球衣', '/img/7e9f431c-6364-4f5f-8ea4-8cebd4531fd8.jpg', 159.00, '儿童', 0, 3, '2025-04-17 16:47:35.000000', null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (5, '贾马尔穆雷', '季后赛神射手', '/img/80324461-5e04-4929-91af-ae8381819cb9.jpg', 20.00, '男子', 0, 100, '2025-04-17 17:05:34.577235', null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (6, '新款球衣', '新款球衣', '/img/37e67c14-d521-46d2-8a01-799709caae1a.avif', 299.00, '女子', 0, 4, '2025-04-17 16:46:46.000000', null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (8, '新款球衣', '女士球衣', '/img/a14b7b68-cc95-47ad-a6e4-03c19729bdf3.avif', 1.00, '女子', null, 1, '2025-04-17 16:46:54.000000', null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (13, '灰熊', '', '/img/a5e1ce8a-324d-4cf4-ade2-45939da5644f.jpg', 20.00, '男子', null, 111, '2025-04-17 16:45:12.000000', '2025-04-17 16:45:12.000000');
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (14, '约基奇', '', '/img/f8d6a684-8140-40ca-8351-fea32a30ac96.jpeg', 599.00, '男子', 1, 20, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (15, '库里', '', '/img/6af5c08a-467f-44f8-a1db-1047cf14bb06.webp', 599.00, '新品', 1, 30, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (16, '掘金球衣', '', '/img/36e5753f-ee37-4372-b8bc-97447c05a29f.avif', 200.00, '新品', null, 10, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (17, '阿龙戈登', '登哥', '/img/a06d31b6-b42f-4a68-95cf-9496735e3362.avif', 399.00, '新品', null, 20, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (18, '卢卡东契奇', 'luka~magic', '/img/42e48a55-519f-43fc-bc0f-026c587e7ac7.jpg', 500.00, '新品', null, 400, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (19, '芝加哥', '', '/img/45a54954-8e98-40c8-b5c4-d72efe94e8d0.avif', 299.00, '女子', null, 30, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (20, '纽约尼克斯', '', '/img/64e4b114-c656-4426-91e4-c8f8fc15686b.avif', 300.00, '女子', null, 20, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (21, '布鲁克林篮网', '致敬三巨头', '/img/8d1c56f6-cc45-4a19-9b81-57d93f71cca4.avif', 300.00, '儿童', null, 22, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (22, '詹姆斯', '', '/img/13c2c158-72e6-4061-948e-50c6a8a041e0.avif', 222.00, '儿童', null, 22, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (23, '库明加', '', '/img/d5299880-4c4f-4181-8a6b-84987c02812e.avif', 400.00, '儿童', null, 20, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (24, '约基奇', '我就是最贵的', '/img/501af9e2-f4fa-4fbd-9552-9c3741f8eca7.avif', 799.00, '男子', null, 20, null, null);
INSERT INTO nike.product (id, name, description, image_url, price, category, recommended, stock, update_time, create_time) VALUES (25, '掘金T', '掘金总冠军', '/img/10921ef2-7b78-440e-b51b-3d34a07c885f.avif', 399.00, '男子', null, 20, null, null);
