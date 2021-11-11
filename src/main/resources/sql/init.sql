-- 客户表
create table customer
(
    id     serial primary key auto_increment comment '自增主键',
    name   varchar(100) comment '姓名',
    gender varchar(20) comment '性别',
    age    int comment '年龄'
) engine = InnoDB
  default charset utf8mb4;

-- 商品表
create table commodity
(
    id           serial primary key auto_increment comment '自增主键',
    name         varchar(100) comment '名称',
    price        decimal comment '价格',
    produce_city varchar(100) comment '产地'
) engine = InnoDB
  default charset utf8mb4;

-- 账户表
create table account
(
    id      serial primary key auto_increment comment '自增主键',
    name    varchar(100) comment '姓名',
    age     int comment '年龄',
    balance decimal comment '余额'
) engine = InnoDB
  default charset utf8mb4;
