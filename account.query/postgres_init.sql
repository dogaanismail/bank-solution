drop database if exists accountingdb;
create database accountingdb
    with
    encoding = 'UTF8'
    lc_collate = 'en_US.utf8'
    lc_ctype = 'en_US.utf8'
    tablespace = pg_default
    connection limit = -1
    owner postgres;
-- choose database
\c accountingdb

create table account
(
    account_id varchar NOT NULL primary key,
    customer_id bigint NOT NULL,
    country varchar NOT NULL
);

create table account_balance
(
    account_balance_id varchar NOT NULL primary key,
    account_id varchar NOT NULL,
    customer_id bigint NOT NULL,
    currency_code varchar NOT NULL,
    balance bigint NOT NULL,
    available_balance bigint NOT NULL
);

create table account_transaction
(
    transaction_id varchar NOT NULL primary key,
    account_id varchar NOT NULL,
    direction varchar NOT NULL,
    amount bigint NOT NULL,
    status varchar NOT NULL,
    transaction_time timestamp without time zone NOT NULL,
    description varchar NOT NULL,
    currency varchar NOT NULL,
    balance_after_txn bigint NOT NULL

);


alter table account owner to postgres;
alter table account_balance owner to postgres;
alter table account_transaction owner to postgres;