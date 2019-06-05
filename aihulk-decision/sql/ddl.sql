create table business
(
	id int auto_increment
		primary key,
	name varchar(32) null,
	name_en varchar(32) null,
	create_time varchar(19) null,
	update_time varchar(19) null,
	operator varchar(32) null
);

create table decision_unit
(
	id int auto_increment
		primary key,
	name varchar(32) null,
	name_en varchar(32) null,
	create_time varchar(19) null,
	update_time varchar(19) null,
	operator varchar(32) null
);

create table fact
(
	id int auto_increment
		primary key,
	name varchar(32) null,
	name_en varchar(32) null,
	code text null,
	type varchar(10) null,
	create_time varchar(19) null,
	update_time varchar(19) null,
	operator varchar(32) null
);

create table unit
(
	id int auto_increment
		primary key,
	name varchar(32) null,
	name_en varchar(32) null,
	create_time varchar(19) null,
	update_time varchar(19) null,
	operator varchar(32) null
);

create table rule_set
(
	id int auto_increment
		primary key,
	name varchar(32) null,
	name_en varchar(32) null,
	create_time varchar(19) null,
	update_time varchar(19) null,
	operator varchar(32) null
);

