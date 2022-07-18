-- 客户表
drop table if exists customer;
create table if not exists customer
(
    id     serial primary key auto_increment comment '自增主键',
    name   varchar(100) comment '姓名',
    gender varchar(20) comment '性别',
    age    int comment '年龄'
) engine = InnoDB
  default charset utf8mb4;

insert into customer(name, gender, age)
values ('ljh', '男', 26);

-- 商品表
drop table if exists commodity;
create table if not exists commodity
(
    id           serial primary key auto_increment comment '自增主键',
    name         varchar(100) comment '名称',
    price        decimal comment '价格',
    produce_city varchar(100) comment '产地'
) engine = InnoDB
  default charset utf8mb4;

-- 账户表
drop table if exists account;
create table if not exists account
(
    id      serial primary key auto_increment comment '自增主键',
    name    varchar(100) comment '姓名',
    age     int comment '年龄',
    balance decimal comment '余额'
) engine = InnoDB
  default charset utf8mb4;

-- 账户表初始化数据
insert into account(id, name, age, balance)
values (1, 'ljh', 26, 2),
       (2, 'lwk', 23, 6000);
