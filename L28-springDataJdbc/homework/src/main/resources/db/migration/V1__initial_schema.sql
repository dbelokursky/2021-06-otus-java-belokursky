drop table if exists client;
drop table if exists address;
drop table if exists phone;

drop sequence if exists client_sequence;
drop sequence if exists address_sequence;
drop sequence if exists phone_sequence;


create sequence client_sequence start with 1 increment by 1;
create sequence address_sequence start with 1 increment by 1;
create sequence phone_sequence start with 1 increment by 1;

create table address
(
    id       bigserial not null primary key,
    street   varchar(300),
    zip_code varchar(10),
    client_id bigint
);

create table client
(
    id         bigserial not null primary key,
    name       varchar(50)
);

create table phone
(
    id           bigserial not null primary key,
    phone_number varchar(20),
    client_id    bigint
);

alter table address add foreign key (client_id) references client(id);
alter table phone add foreign key (client_id) references client(id);