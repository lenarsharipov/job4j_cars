create table auto_users
(
    id          serial primary key,
    login       varchar not null unique,
    password    varchar not null
);

create table auto_posts
(
    id              serial primary key,
    description     varchar                         not null,
    created         timestamp                       not null,
    auto_user_id    int references auto_users (id)  not null
);