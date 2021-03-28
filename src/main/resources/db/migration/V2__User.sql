alter table users."user"
    rename column login to username;

alter table users."user"
    add role varchar(64);