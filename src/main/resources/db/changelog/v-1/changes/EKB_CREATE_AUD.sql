--liquibase formatted sql

--changeset akulov_a:1

create table user_model
(
    id bigserial not null
        constraint user_model_pk
            primary key,
    login varchar not null,
    password varchar not null,
    created_date date not null,
    enable boolean not null,
    position varchar not null,
    fio varchar not null,
    department varchar not null
);

alter table user_model owner to postgres;

create unique index user_model_login_uindex
    on user_model (login);

create table user_role
(
    user_id bigint not null
        constraint user_role_user_model_id_fk
            references user_model,
    role varchar,
    constraint user_role_pk
        unique (user_id, role)
);
