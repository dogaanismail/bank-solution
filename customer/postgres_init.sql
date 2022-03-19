drop database if exists customerdb;
create database customerdb
    with
    encoding = 'UTF8'
    lc_collate = 'en_US.utf8'
    lc_ctype = 'en_US.utf8'
    tablespace = pg_default
    connection limit = -1
    owner postgres;
-- choose database
\c customerdb

--customer table
create table customer
(
    customer_id serial not null primary key,
    country     varchar not null
);
alter table customer
    owner to postgres;

create sequence if not exists customer_customer_id_seq as integer;
alter sequence customer_customer_id_seq owner to postgres;
alter sequence customer_customer_id_seq owned by customer.customer_id;