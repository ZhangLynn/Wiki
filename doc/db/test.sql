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
values (1, '测试');

create table demo
(
    id   bigint      not null comment 'id',
    name varchar(50) null comment '名称',
    constraint test_pk
        primary key (id)
) engine = innodb
  default charset = utf8mb4 comment ='测试';

drop table if exists `test`;
create table ebook
(
    id           bigint       not null comment 'id',
    name         varchar(50)  null comment '名称',
    category1_id bigint       null comment '分类1',
    category2_id bigint       null comment '分类2',
    description  varchar(200) null comment '描述',
    cover        varchar(200) null comment '封面',
    doc_count    int          null comment '文档数',
    view_count   int          null comment '阅读数',
    vote_count   int          null comment '点赞数',
    constraint ebook_pk
        primary key (id)
) engine = innodb
  default charset = utf8mb4 comment '电子书';

insert into ebook (id, name, description)
values (1, 'Spring boot 入门教程', '零基础入门 Java 开发， 企业级应用开发最佳框架');
insert into ebook (id, name, description)
values (2, 'Vue 入门教程', '零基础入门 Vue 开发， 企业级应用开发最佳框架');

