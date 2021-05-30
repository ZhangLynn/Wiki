drop table if exists `test`;
create table test
(
    id       bigint      not null comment 'id',
    name     varchar(50) null comment '名称',
    password varchar(50) null comment '密码',
    constraint test_pk
        primary key (id)
) engine = innodb
  default charset = utf8mb4 comment ='测试';

drop table if exists `demo`;
create table demo
(
    id   bigint      not null comment 'id',
    name varchar(50) null comment '名称',
    constraint test_pk
        primary key (id)
) engine = innodb
  default charset = utf8mb4 comment ='测试';

insert into demo (id, name)
values (1, '测试')