-- 查看事务隔离级别
select @@global.transaction_isolation;
select @@session.transaction_isolation;
select @@transaction_isolation;

-- 结果查询脚本
select id, balance
from account
where id in (1, 2);

-- 在事务会话2.2的步骤分别执行三个过程，观察查询结果
set global transaction isolation level read uncommitted;
set session transaction isolation level read uncommitted;
set transaction isolation level read uncommitted;

-- 恢复隔离级别
set global transaction isolation level repeatable read;
set session transaction isolation level repeatable read;
set transaction isolation level repeatable read;