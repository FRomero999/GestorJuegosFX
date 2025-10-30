create table user
(
    id       int auto_increment primary key,
    email    varchar(255) null,
    password varchar(255) null,
    is_admin tinyint(1)   null
);

INSERT INTO user (id, email, password, is_admin) VALUES (1, 'f@cesur.com', '1234', 1);
INSERT INTO user (id, email, password, is_admin) VALUES (2, 'r@cesur.com', '1234', 0);
INSERT INTO user (id, email, password, is_admin) VALUES (3, 'g@cesur.com', '1234', 0);