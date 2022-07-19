-- 查看事务隔离级别
show default_transaction_isolation;

show transaction_isolation;

-- 结果查询脚本
select *
from account;

-- 设置RU级别
start transaction ;
set default_transaction_isolation = 'read uncommitted';
show transaction_isolation;
commit;

-- 设置RR级别
start transaction;
set transaction_isolation = 'repeatable read';
commit;

-- 设置串行化级别
start transaction;
set transaction_isolation = 'serializable';
commit;

-- 恢复隔离级别RC
start transaction;
set transaction_isolation = 'read committed';
commit;