drop table if exists EXPRESSIONS;
create table EXPRESSIONS
(
	ID BIGINT auto_increment
		primary key,
	CREATED_ON TIMESTAMP not null,
	EXPRESSION VARCHAR not null,
	RESULT DECIMAL not null
);

drop table if exists NUMBERS;
create table NUMBERS
(
	ID BIGINT auto_increment
		primary key,
	EXPRESSION_ID BIGINT not null,
	NUMBER DECIMAL not null
);

drop table if exists OPERATIONS;
create table OPERATIONS
(
    ID            BIGINT auto_increment
        primary key,
    EXPRESSION_ID BIGINT  not null,
    NAME          INTEGER not null
);