-- liquibase formatted sql

-- changeset anton:3
create table ads
(
    ad_id     bigint generated by default as identity,
    description  varchar(255),
    price        integer,
    title        varchar(255) NOT NULL,
    ad_image_url varchar(255),
    user_id bigint NOT NULL,
    primary key (ad_id),
    foreign key (ad_image_url) references image(url),
    foreign key (user_id) references users(id)
);