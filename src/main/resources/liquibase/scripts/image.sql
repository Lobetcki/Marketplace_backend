-- liquibase formatted sql

-- changeset anton:1
create table image (
   url bigint not null unique,
   bytes bytea,
--    file_path varchar(255),
--    file_size bigint not null,
--    media_type varchar(255),
   primary key (url)
);