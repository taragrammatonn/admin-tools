alter table users."user"
    add column username varchar(64);

alter table users."user"
    add role varchar(64);

create unique index user_user_option_id_uindex
    on users."user" (user_option_id);

create unique index user_option_user_id_uindex
    on users.user_option (user_id);

create sequence users.user_option_id_seq;

alter table users.user_option alter column id set default nextval('users.user_option_id_seq');

alter sequence users.user_option_id_seq owned by users.user_option.id;

alter table users."user"
    add constraint user_user_option_id_fk
        foreign key (user_option_id) references users.user_option;

alter table users.user_option
    add constraint user_option_user_user_option_id_fk
        foreign key (id) references users."user" (user_option_id);