-- 账户表
drop table if exists account;
-- 账户表
create table if not exists account
(
    id      serial primary key,
    name    varchar(100) ,
    age     int,
    balance decimal
);

-- 账户表初始化数据
insert into account(id, name, age, balance)
values (1, 'ljh', 26, 2),
       (2, 'lwk', 23, 6000);