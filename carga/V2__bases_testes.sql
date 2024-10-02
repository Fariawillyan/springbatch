use master
GO

DROP DATABASE TESTE_1
DROP DATABASE TESTE_2
DROP DATABASE TESTE_3
GO

CREATE DATABASE TESTE_1
GO

USE TESTE_1
GO

CREATE TABLE TB_USUARIO (
	id int identity,
	numero int,
	nome varchar(250),
	nascimento date,
	primary key (id)
);
GO


CREATE DATABASE TESTE_2
GO

USE TESTE_2
GO

CREATE TABLE TB_CLIENTE (
	id int identity,
	emp_numero int,
	cartao int,
	nome varchar(250),
	dt_nasc date,
	primary key (id)
);
GO

CREATE DATABASE TESTE_3
GO

USE TESTE_3
GO

CREATE TABLE TB_USU_CLI (
	id int identity,
	num_cartao int,
	nome varchar(250),
	data_nascimento date,
	id_plano int,
	primary key (id)
);
GO

use master
GO

DROP DATABASE TESTE_1
DROP DATABASE TESTE_2
DROP DATABASE TESTE_3
GO

CREATE DATABASE TESTE_1
GO

USE TESTE_1
GO

CREATE TABLE TB_USUARIO (
	id int identity,
	numero int,
	nome varchar(250),
	nascimento date,
	primary key (id)
);
GO


CREATE DATABASE TESTE_2
GO

USE TESTE_2
GO

CREATE TABLE TB_CLIENTE (
	id int identity,
	emp_numero int,
	cartao int,
	nome varchar(250),
	dt_nasc date,
	primary key (id)
);
GO

CREATE DATABASE TESTE_3
GO

USE TESTE_3
GO

CREATE TABLE TB_USU_CLI (
	id int identity,
	num_cartao int,
	nome varchar(250),
	data_nascimento date,
	id_plano int,
	primary key (id)
);
GO