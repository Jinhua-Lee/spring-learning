
-- 是否采用【独立表空间】存储
show variables like 'innodb_file_per_table';

select count(*)
from student_memory;

truncate student_memory;

insert into student_memory(name, age, birthday, last_update_time) values('ljh', 26, '1996-08-20', '2022-12-25 23:06:00');