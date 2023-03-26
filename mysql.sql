drop schema if exists stackoverflow;

create schema stackoverflow;

use stackoverflow;

create table users (
	user_id bigint primary key unique not null auto_increment,
	f_name varchar(50) not null,
	l_name varchar(50) not null,    
	email varchar(50) unique not null,
	password varchar(80) not null,
    score bigint not null
);

create table questions (
	id bigint primary key unique not null auto_increment,
    title varchar(50) not null,
    text varchar(300) not null,
    picture varchar(100) not null,
    creation_date dateTime not null,
    user_id bigint not null
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
    text varchar(300) not null,
    picture varchar(100) not null,
    creation_date dateTime not null,
    question_id bigint not null,
    user_id bigint not null
);

insert into users values (1, "fname1", "lname1", "user1@yahoo.com", "$2a$10$ERfv9A0s5giOwmDk1yieg.WgsTUG41PRMg2hRiFIkiDWmkH4z11vS", 0);
insert into users values (2, "fname2", "lname2", "user2@yahoo.com", "$2a$10$xuEhInb351D.TpKxQhbTUu5phytc1q9E8TYqJOmGwG3tMsOf3/jlq", 0);
insert into users values (3, "fname1", "lname3", "user3@yahoo.com", "$2a$10$xgnXPC7a0PFuYqIbswszj.n4hqT37WkWT31N3202/Chwa4ycJptpq", 0);

insert into questions values (1, "question1", "text question 1", "picture1.jpg", now(), 1);
insert into questions values (2, "question2", "text question 2", "picture2.jpg", now(), 1);
insert into questions values (3, "question3", "text question 3", "picture3.jpg", now(), 1);

insert into answers values (1, "answer1", "picture1.jpg", now(), 1, 1);
insert into answers values (2, "answer2", "picture2.jpg", now(), 1, 2);
insert into answers values (3, "answer3", "picture3.jpg", now(), 2, 1);
