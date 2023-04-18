-- liquibase formatted sql

-- changeset anton:1
create table image (
   url varchar(255) not null,
   bytes bytea,
   file_path varchar(255),
   file_size bigint not null,
   media_type varchar(255),
   primary key (url)
);

-- CREATE TABLE student
-- (
--     id_student BIGSERIAL PRIMARY KEY,
--     name_student VARCHAR(255) NOT NULL,
--     age_student integer NOT NULL,
--     faculty_faculty_id bigint,
--     foreign key (faculty_faculty_id) references faculty (faculty_id)
-- );

-- -- changeset anton:2.1
-- alter table  hogwarts.public.student
--     add constraint constr_age_student check (age_student > 15);
