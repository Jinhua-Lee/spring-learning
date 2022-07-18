-- 1. 关闭自动提交
set @@autocommit = 0;

-- 2. 开启事务
start transaction;

-- 2.1 操作
update account set balance = balance - 3000 where id = 2;
update account set balance = balance + 3000 where id = 1;

-- 2.2 对比：与另一个会话，查询中间结果进行对比
select id, balance
from account
where id in (1, 2);

-- 2.3 回滚
rollback;