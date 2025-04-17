create table products
(
    id             bigint auto_increment
        primary key,
    category       varchar(255)   null,
    created_at     datetime(6)    null,
    description    varchar(255)   null,
    image_url      varchar(255)   null,
    is_recommended bit            null,
    name           varchar(255)   null,
    price          decimal(38, 2) null,
    stock          int            null,
    updated_at     datetime(6)    null
);

