create sequence `hibernate_sequence`;

create table person
(
    id                   bigint       not null
        primary key,
    created              datetime(6)  null,
    created_by           varchar(255) null,
    creator              longtext     null,
    editor               longtext     null,
    storage_map          varchar(255) null,
    updated              datetime(6)  null,
    updated_by           varchar(255) null,
    active               bit          not null,
    attachment_list_data longtext     null,
    birth                date         not null,
    email                varchar(256) not null,
    name                 varchar(255) not null,
    password             varchar(255) not null
);

create table role
(
    id   bigint       not null
        primary key,
    name varchar(255) null
);

create table person_roles
(
    roles_id  bigint not null,
    person_id bigint not null,
    constraint FK7s0o6k42r2stk8tc0qk2r5t4i
        foreign key (roles_id) references role (id),
    constraint FKs955luj19xyjwi3s1omo1pgh4
        foreign key (person_id) references person (id)
);

create table shop
(
    id          bigint       not null
        primary key,
    created     datetime(6)  null,
    created_by  varchar(255) null,
    creator     longtext     null,
    editor      longtext     null,
    storage_map varchar(255) null,
    updated     datetime(6)  null,
    updated_by  varchar(255) null,
    name        varchar(255) not null,
    slug        varchar(128) not null
);

create table product
(
    id       bigint       not null
        primary key,
    name     varchar(255) not null,
    quantity int          not null,
    slug     varchar(255) not null,
    id_shop  bigint       not null,
    constraint FKyl05c7yfqa2vygwwg7na1hcv
        foreign key (id_shop) references shop (id),
    constraint quantity
        check (`quantity` >= 0)
);

create table template
(
    id         bigint       not null
        primary key,
    created    datetime(6)  null,
    created_by varchar(255) null,
    creator    longtext     null,
    editor     longtext     null,
    map_data   longtext     null,
    updated    datetime(6)  null,
    updated_by varchar(255) null
);

