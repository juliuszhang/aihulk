create table business
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '业务';

create table chain
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	business_id int(11) null comment '所属业务id',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '决策链';

create table fact
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	business_id int(11) null comment '所属业务id',
	code text null comment '事实代码',
	code_type varchar(32) null comment '事实代码类型',
	result_type varchar(10) null comment '返回值类型',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '事实';

create table variable
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	business_id int(11) null comment '所属业务id',
	type varchar(10) null comment '变量类型',
	merge_strategy int(11) null comment '合并逻辑',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '变量';

create table unit
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	business_id int(11) null comment '所属业务id',
	type tinyint(1) null comment '执行单元的type',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '执行单元';


create table logic
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	relation_id int(11) null comment '关联id',
	unit_id int(11) null comment '执行单元id',
	exec_type tinyint(1) null comment '执行类型',
	type tinyint(1) null comment '表达式类型',
	express text null comment '表达式',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '执行单元';

create table flow_rule
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	src_type int(11) null comment 'src 类型（执行单元、执行单元组）',
	dest_type int(11) null comment 'dest 类型（执行单元、执行单元组)',
	logic_id int(11) null comment 'logic id',
	src_id int(11) null comment 'src id',
	dest_id int(11) null comment 'dest id',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '执行单元';

create table unit_group
(
	id int auto_increment
		primary key comment '主键',
	name varchar(32) null comment '名称',
	name_en varchar(32) null comment '英文名',
	business_id int(11) null comment '所属业务id',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '执行单元组';

create table unit_fact_relation
(
	id int auto_increment
		primary key comment '主键',
	unit_id int(11) null comment '执行单元id',
	fact_id int(11) null comment '事实id',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '执行单元事实关联表';

create table unit_unit_group_relation
(
	id int auto_increment
		primary key comment '主键',
	unit_id int(11) null comment '执行单元id',
	unit_group_id int(11) null comment '执行单元组id',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '执行单元执行单元组关联表';


create table chain_unit_relation
(
	id int auto_increment
		primary key comment '主键',
	chain_id int(11) null comment '决策链id',
	unit_id int(11) null comment '执行单元id',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '决策链执行单元关联表';

create table chain_unit_group_relation
(
	id int auto_increment
		primary key comment '主键',
	chain_id int(11) null comment '决策链id',
	unit_group_id int(11) null comment '执行单元组id',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '决策链执行单元组关联表';


create table `user`
(
	id int auto_increment
		primary key comment '主键',
	username varchar(32) null comment '用户名',
	password varchar (64) null comment '密码',
	email varchar (64) null comment '邮箱',
	mobile varchar (11) null comment '手机',
	email_checked tinyint (1) null comment '邮箱是否认证',
	token varchar (64) null comment '参与邮箱认证的token',
	create_time varchar(19) null comment '创建时间',
	update_time varchar(19) null comment '更新时间',
	deleted tinyint(1) null DEFAULT 0 comment '逻辑删除字段',
	operator varchar(32) null comment '操作人'
) comment '用户';
