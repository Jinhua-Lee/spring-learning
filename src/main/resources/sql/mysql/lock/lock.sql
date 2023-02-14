-- 当前读：读最新版本
-- 快照读：读历史版本，以提高并发性能
    -- 前提：非serial级别，因为serial级别会退化为【当前读】

-- 共享锁

select id, name, age
from student_innodb
where id = 1
    lock in share mode;

select id, name, age
from student_innodb
where id = 1
    for
update;

-- 普通的update, insert, delete也是排他锁

