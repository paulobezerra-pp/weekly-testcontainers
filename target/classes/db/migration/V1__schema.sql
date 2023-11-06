create table if not exists tb_users(
    id bigint primary key,
    name text
);


create sequence if not exists users_seq
    start with 1
    increment by 1
    minvalue 1
    maxvalue 9999
    no cycle;