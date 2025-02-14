use `test`;

create table if not exists member
(
    id           int          not null auto_increment comment 'pk',
    email        VARCHAR(50)  not null comment '이메일',
    password     VARCHAR(255) not null comment '비밀번호',
    access_token VARCHAR(255) null comment '엑세스토큰',
    created_at   datetime     not null default current_timestamp comment '생성일시',
    updated_at   datetime     not null default current_timestamp on update current_timestamp comment '수정일시',
    primary key (id),
    unique (email)

) comment '회원 테이블';

create index email_index on member (email);



create table if not exists favorite_folder
(
    id         int      not null auto_increment comment 'pk',
    member_id  int      not null comment 'member pk',
    name       varchar(255) comment '서랍 이름',
    created_at datetime not null default current_timestamp comment '생성일시',
    updated_at datetime not null default current_timestamp on update current_timestamp comment '수정일시',
    primary key (id),
    unique (member_id, name)
) comment '찜 서랍 테이블';


create index created_at_index on favorite_folder (created_at);

create table if not exists favorite_product
(
    id         int      not null auto_increment comment 'pk',
    member_id  int      not null comment 'member pk',
    folder_id  int      not null comment 'favorite_folder pk',
    product_id int      not null comment 'product pk',
    created_at datetime not null default current_timestamp comment '생성일시',
    updated_at datetime not null default current_timestamp on update current_timestamp comment '수정일시',
    primary key (id),
    unique (member_id, product_id)
) comment '찜 상품목록 테이블';

create index created_at_index on favorite_product (created_at);

create table if not exists product
(
    id            int          not null auto_increment comment 'pk',
    name          varchar(255) not null comment '상품이름',
    thumbnail_url varchar(255) not null comment '상품 image url',
    price         int          not null comment '가격',
    created_at    datetime     not null default current_timestamp comment '생성일시',
    updated_at    datetime     not null default current_timestamp on update current_timestamp comment '수정일시',
    deleted_at    datetime     null comment '삭제일시',
    primary key (id)
) comment '찜 상품목록 테이블';