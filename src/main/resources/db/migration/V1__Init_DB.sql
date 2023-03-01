create table currencies (
    id  bigserial not null,
    name varchar(255) not null,
    primary key (id)
);
create table exchange_rates (
    id  bigserial not null,
    name varchar(255) not null,
    rate float8 not null,
    primary key (id)
);
create table roles (
    id int8 not null,
    name varchar(255),
    primary key (id)
);
create table transactions (
    id  bigserial not null,
    created timestamp,
    name varchar(255),
    primary key (id)
);
create table user_currency (
    amount float8,
    currency_id int8 not null,
    user_id int8 not null,
    primary key (currency_id, user_id)
);
create table user_roles (
    user_id int8 not null,
    role_id int8 not null
);
create table users (
    id  bigserial not null,
    created timestamp,
    email varchar(255) not null,
    password varchar(255) not null,
    updated timestamp,
    username varchar(255) not null,
    primary key (id)
);
alter table if exists currencies
    add constraint UK_a2yxotynwqjrmkq71won77vui
    unique (name);

alter table if exists exchange_rates
    add constraint UK_ea8aeepbfv99cf3mk34so2j0s
    unique (name);

alter table if exists users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7
    unique (email);

alter table if exists users
    add constraint UK_r43af9ap4edm43mmtq01oddj6
    unique (username);

alter table if exists user_currency
    add constraint FKs7y02u7e9or2q0301pfglctdo
    foreign key (currency_id)
    references currencies;

alter table if exists user_currency
    add constraint FKgcrwvwl1set3v9gkffnqo1y24
    foreign key (user_id)
    references users;

alter table if exists user_roles
    add constraint FKh8ciramu9cc9q3qcqiv4ue8a6
    foreign key (role_id)
    references roles;

alter table if exists user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f
    foreign key (user_id)
    references users;