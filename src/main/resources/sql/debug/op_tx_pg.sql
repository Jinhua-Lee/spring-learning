--===== PG事务隔离级别，没有RU，默认为RC处理 =====--

-- 1. 开启事务
start transaction;
show transaction_isolation;

-- 2. 操作

-- RR级别测试
insert into account(id, name, age, balance)
values (3, 'alpha', 300, 8888);

-- 3. 对比：与另一个会话，查询中间结果进行对比
select *
from account;

-- 4. 提交 / 回滚
commit;
rollback;