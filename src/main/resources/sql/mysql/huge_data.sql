-- 1. 创建InnoDB表
create table if not exists student_innodb
(
    id               serial primary key auto_increment comment '主键ID',
    name             varchar(200) comment '姓名',
    age              int comment '年龄',
    birthday         date comment '生日',
    last_update_time datetime comment '上次更新时间'
) engine = InnoDB
  default charset = utf8mb4;

-- 2. 创建Memory表
create table if not exists student_memory
(
    id               serial primary key auto_increment comment '主键ID',
    name             varchar(200) comment '姓名',
    age              int comment '年龄',
    birthday         date comment '生日',
    last_update_time datetime comment '上次更新时间'
) engine = Memory
  default charset = utf8mb4;

-- 3. 函数：生成指定长度的字符串
create
    definer = 'root'@'localhost' function rand_str(n int) returns varchar(255) charset utf8mb4 deterministic
begin
    -- 字符范围
    declare chars_str varchar(100) default 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    declare return_str varchar(100) default '';
    declare i int default 0;
    while char_length(return_str) < n
        do
            set return_str = concat(return_str, substring(chars_str, floor(1 + rand() * 62)));
            set i = i + 1;
        end while;
    return return_str;
end;

-- 4. 创建函数：随机日期
create
    definer = 'root'@'localhost' function rand_date_time(sd datetime, ed datetime) returns datetime deterministic
begin
    -- 日期范围
    declare sub int default 0;
    declare ret datetime;
    set sub = abs(unix_timestamp(ed) - unix_timestamp(sd));
    set ret = date_add(sd, interval floor(1 + rand() * (sub - 1)) second);
    return ret;
end;

-- 5. 创建存储过程：插入n条数据
create
    definer = 'root'@'localhost' procedure add_student_memory(in n int)
begin
    declare i int default 1;
    while (i <= n)
        do
            insert into student_memory(name, age, birthday, last_update_time)
            values (rand_str(20), floor(rand() * 60),
                    rand_date_time('2000-01-01 00:00:00', '2021-01-01 00:00:00'),
                    rand_date_time('2000-01-01 00:00:00', '2021-01-01 00:00:00'));
            set i = i + 1;
        end while;
end;

-- 6. 调用存储过程
call add_student_memory(1000000);

## 初始化过程会出现 table is full，需要修改/etc/my.cnf文件，在[mysqld]结点加入配置项
-- tmp_table_size = 1024M
-- max_heap_table_size = 1024M


-- 7. 写入正式表
insert into student_innodb
select *
from student_memory;