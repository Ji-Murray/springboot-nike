create table user
(
    username varchar(255) not null,
    password varchar(255) not null,
    id       bigint auto_increment
        primary key,
    email    varchar(50)  null
);

INSERT INTO nike.user (username, password, id, email) VALUES ('123', '123456', 1, null);
INSERT INTO nike.user (username, password, id, email) VALUES ('纪子涵', '123456', 2, null);
INSERT INTO nike.user (username, password, id, email) VALUES ('lll', '123456', 4, null);
