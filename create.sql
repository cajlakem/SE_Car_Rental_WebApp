create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table users (id bigint not null, first_name varchar(255), last_name varchar(255), user_name varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table users (id bigint not null, first_name varchar(255), last_name varchar(255), user_name varchar(255), primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table users (id bigint not null, first_name varchar(255) not null, last_name varchar(255) not null, user_name varchar(255) not null, primary key (id)) engine=InnoDB
