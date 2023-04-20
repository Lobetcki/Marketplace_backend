-- liquibase formatted sql

-- changeset anton:4
create table comments (
      id bigint generated by default as identity NOT NULL,
      created_at_date timestamp,
      text text NOT NULL,
      ads_id bigint NOT NULL,
      users_id bigint NOT NULL,
      primary key (id),
      foreign key (ads_id) references ads(id),
      foreign key (users_id) references users(id)

);