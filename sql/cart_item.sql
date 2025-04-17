create table cart_item
(
    id          bigint auto_increment
        primary key,
    user_id     bigint         not null,
    product_id  bigint         not null,
    size        varchar(10)    not null,
    quantity    int            not null,
    price       decimal(10, 2) not null,
    create_time varchar(50)    not null,
    update_time varchar(50)    not null,
    constraint FKjcyd5wv4igqnw413rgxbfu4nv
        foreign key (product_id) references product (id)
);

create index FKqkqmvkmbtiaqn2nfqf25ymfs2
    on cart_item (product_id);

create index user_id
    on cart_item (user_id);

