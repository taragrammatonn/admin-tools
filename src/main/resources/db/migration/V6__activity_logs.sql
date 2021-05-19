create schema if not exists activity_logs;

create table if not exists activity_logs.user_history
(
    id          bigserial not null
constraint user_history_pk
primary key,
    action      varchar(40),
    user_id     bigint,
    action_time timestamp default now(),
    login_time  timestamp,
    logout_time timestamp
);

alter table user_history
owner to postgres;

create unique index if not exists user_history_id_uindex
on activity_logs.user_history (id);

