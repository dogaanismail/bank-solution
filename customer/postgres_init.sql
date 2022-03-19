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
    customer_id bigint  not null primary key,
    country     varchar not null
);
alter table customer
    owner to postgres;

ALTER TABLE postgres.customer ALTER COLUMN customer_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.customer_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);