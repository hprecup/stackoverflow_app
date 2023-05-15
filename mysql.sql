drop schema if exists stackoverflow;

create schema stackoverflow;

use stackoverflow;

create table users (
	user_id bigint primary key unique not null auto_increment,
	f_name varchar(50) not null,
	l_name varchar(50) not null,
    username varchar(50) unique not null,
	email varchar(50) unique not null,
	password varchar(80) not null,
    score float not null
);

create table questions (
	id bigint primary key unique not null auto_increment,
    title varchar(50) not null,
    text varchar(400) not null,
    picture varchar(100) not null,
    creation_date dateTime not null,
    user_id bigint not null,
    vote_count bigint
);

create table tags (
	tag_id bigint primary key unique not null auto_increment,
    tag_name varchar(20) unique not null
);

create table question_tag (
	id bigint primary key unique not null auto_increment,
    question_id bigint not null,
    tag_id bigint not null
);

create table answers (
	id bigint primary key unique not null auto_increment,
    text varchar(400) not null,
    picture varchar(100) not null,
    creation_date dateTime not null,
    question_id bigint not null,
    user_id bigint not null,
    vote_count bigint
);

create table roles (
	id bigint primary key unique not null auto_increment,
    role varchar(20) not null
);

create table user_role (
	id bigint primary key unique not null auto_increment,
    user_id bigint not null,
    role_id bigint not null
);

create table question_votes (
	id bigint primary key unique not null auto_increment,
    question_id bigint not null,
    user_id bigint not null,
    vote_type varchar(20) not null
);

create table answer_votes (
	id bigint primary key unique not null auto_increment,
    answer_id bigint not null,
    user_id bigint not null,
    vote_type varchar(20) not null
);

insert into roles values (1, "USER");
insert into roles values (2, "MODERATOR");

insert into users values (1, "fname1", "lname1","username1", "user1@yahoo.com", "$2a$10$CfmIIsNTB7JR2bC/Mo9dxezfA2vT2e9K.RBiH1nyYXZ1vORMX9O8u", 0);
insert into users values (2, "fname2", "lname2","username2", "user2@yahoo.com", "$2a$10$NErPHIXKVr.VLagVhre1n.alWMRGY36E4XdJ52J7.UATP/sBuKExK", 0);
insert into users values (3, "fname1", "lname3","username3", "user3@yahoo.com", "$2a$10$p7I5ovLRVK4.5IRcvI2M4O/aMLEqHk8uOq/adXIbau/IByMkZfcs2", 0);
insert into users values (4, "admin", "admin","admin", "admin@yahoo.com", "$2a$10$7ohn7xL0xn2gpBouZlAXMOJW8G0KZc91DPAF6FTOS8AzjxjFlW2F2", 0);

insert into user_role values (1, 1, 1);
insert into user_role values (2, 2, 1);
insert into user_role values (3, 3, 1);
insert into user_role values (4, 4, 1);
insert into user_role values (5, 4, 2);

insert into questions values (1, "question1", "text question 1", "picture1.jpg", now(), 2, 0);
insert into questions values (2, "question2", "text question 2", "picture2.jpg", now(), 1, 0);
insert into questions values (3, "question3", "text question 3", "picture3.jpg", now(), 1, 0);
insert into questions values (4, "question4", "text question 4", "picture4.jpg", now(), 2, 0);

insert into answers values (1, "answer1", "picture1.jpg", now(), 1, 1, 0);
insert into answers values (2, "answer2", "picture2.jpg", now(), 1, 2, 0);
insert into answers values (3, "answer3", "picture3.jpg", now(), 2, 1, 0);

insert into tags values (1,"Java"), (2,"Python"), (3,"C++"), (4,"Angular"), (5,"Javascript");

insert into question_tag values (1, 1, 2);

