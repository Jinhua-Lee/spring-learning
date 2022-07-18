-- 查看事务隔离级别
show default_transaction_isolation;

show transaction_isolation;

-- 结果查询脚本
select *
from account;

-- 在事务会话2.2的步骤分别执行两个过程，观察查询结果
set default_transaction_isolation = 'read uncommitted';
set default_transaction_isolation = 'repeatable read';
set default_transaction_isolation = 'serializable';

-- 恢复隔离级别
set default_transaction_isolation = 'read committed';