drop table if exists client;
drop table if exists employee;
drop table if exists movie;
drop table if exists place;
drop table if exists room;
drop table if exists row;
drop table if exists session;
drop table if exists ticket;

create table client(
	Id bigserial primary key,
	firs_name varchar(32),
	last_name varchar(32),
	age smallint,
	email varchar(256)
);

create table employee(
	Id bigserial primary key,
	first_name varchar(32) not null,
	last_name varchar(32) not null

);

create table movie(
	Id bigserial primary key,
	name varchar(128) not null,
	category varchar(64) not null,
	length smallint not null,
	description varchar(2048),
	age_limit smallint not null
);

create table place(
	Id bigserial primary key,
	room_id bigint not null,
	session_id bigint not null,
	row_nuber smallint not null,
	seat_number smallint not null,
	is_sold bool not null default false
);

create table room(
	Id bigserial primary key,
	capacity smallint not null,
	layout smallint[] not null
);

create table session(
	Id bigserial primary key,
	movie_id bigint not null,
	room_id bigint not null,
	start_time timestamp not null
);

create table ticket(
	Id bigserial primary key,
	session_id bigint not null,
	client_id bigint not null,
	row_number smallint not null,
	seat_number smallint not null,
	price decimal(10,2) not null
);