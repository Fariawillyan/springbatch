USE master
GO

--drop database CargaMaster
--GO

CREATE DATABASE CargaMaster;
GO

USE CargaMaster;
GO

CREATE TABLE TB_TIPO_CONEXAO (
	id_tipo_conexao int identity,
	descricao varchar(250) not null,
	constraint pk_tbTipo_Conexao primary key (id_tipo_conexao)
);
GO

INSERT INTO TB_TIPO_CONEXAO (descricao) VALUES ('SQL_SERVER');
INSERT INTO TB_TIPO_CONEXAO (descricao) VALUES ('INFORMIX');
INSERT INTO TB_TIPO_CONEXAO (descricao) VALUES ('ORACLE');
GO

-- Não lembro o que é!!! =\
/*
CREATE TABLE TB_TIPO_CARGA (
	id_tipo_carga int identity,
	descricao varchar(250) not null,
	constraint pk_TB_TIPO_CARGA primary key (id_tipo_carga)
);
GO
*/

CREATE TABLE id_tipo_delimitador (
	TB_TIPO_DELIMITADOR int identity,
	descricao varchar(250) not null,
	delimitador varchar(5) not null,
	usa_posicionamento bit not null,
	constraint pk_TB_TIPO_DELIMITADOR primary key (id_tipo_delimitador)
);
GO

INSERT INTO TB_TIPO_DELIMITADOR (descricao, delimitador, usa_posicionamento) values ('Posicionamento', '', 1);
INSERT INTO TB_TIPO_DELIMITADOR (descricao, delimitador, usa_posicionamento) values ('Ponto e Virgula', ';', 0);
INSERT INTO TB_TIPO_DELIMITADOR (descricao, delimitador, usa_posicionamento) values ('Pipe', '|', 0);
INSERT INTO TB_TIPO_DELIMITADOR (descricao, delimitador, usa_posicionamento) values ('Tabulação', 'TAB', 0);
GO

CREATE TABLE TB_TIPO_CAMPO (
	id_tipo_campo int identity,
	descricao varchar(250) not null,
	constraint pk_TB_TIPO_CAMPO primary key (id_tipo_campo)
);
GO

INSERT INTO TB_TIPO_CAMPO (descricao) VALUES ('STRING');
INSERT INTO TB_TIPO_CAMPO (descricao) VALUES ('INTEGER');
INSERT INTO TB_TIPO_CAMPO (descricao) VALUES ('DECIMAL');
INSERT INTO TB_TIPO_CAMPO (descricao) VALUES ('BOOLEAN');
INSERT INTO TB_TIPO_CAMPO (descricao) VALUES ('DATA');
INSERT INTO TB_TIPO_CAMPO (descricao) VALUES ('HORA');
INSERT INTO TB_TIPO_CAMPO (descricao) VALUES ('DATA_HORA');
GO

CREATE TABLE TB_CAMPO (
	id_campo int identity,
	campo varchar(250) not null,
	id_tipo_campo int not null,
	constraint pk_TB_CAMPO primary key (id_campo),
	constraint fk_TB_CAMPO_TB_TIPO_CAMPO foreign key (id_tipo_campo) references TB_TIPO_CAMPO (id_tipo_campo)
);
GO

CREATE TABLE TB_CARGA (
	id_carga int identity,
	descricao varchar(250) not null,
	ems_numero int not null,
	--id_tipo_carga int not null,
	id_tipo_delimitador int not null,
	nome_arquivo varchar(250) not null,
	ftp_host varchar(250) not null,
	ftp_porta varchar(250) not null,
	ftp_usuario varchar(250) not null,
	ftp_senha varchar(250) not null,
	ftp_path varchar(250) not null,
	interacao_posicao_inicial int,
	interacao_posicao_final int,
	usa_identificador_tabela bit not null constraint df_TB_CARGA_usa_identificador_tabela default 0,
	identificador_tabela_posicao_inicial int,
	identificador_tabela_posicao_final int,
	constraint pk_TB_CARGA primary key (id_carga),
	--constraint fk_TB_CARGA_tbTipo_carga foreign key (id_tipo_carga) references tbTipo_carga (id_tipo_carga),
	constraint fk_TB_CARGA_TB_TIPO_DELIMITADOR foreign key (id_tipo_delimitador) references TB_TIPO_DELIMITADOR (id_tipo_delimitador)
);
GO

CREATE TABLE TB_CARGA_CAMPO (
	id_carga_campo int identity,
	id_carga int not null,
	id_campo int not null,
	ordem int not null,
	posicao_inicial int,
	posicao_final int,
	tamanho int,
	constraint pk_TB_CARGA_CAMPO primary key (id_carga_campo),
	constraint fk_TB_CARGA_CAMPO_TB_CARGA foreign key (id_carga) references TB_CARGA (id_carga),
	constraint fk_TB_CARGA_CAMPO_TB_CAMPO foreign key (id_campo) references TB_CAMPO (id_campo)
);
GO

CREATE TABLE TB_CARGA_DESTINO (
	id_carga_destino int identity,
	id_carga int not null,
	descricao varchar(250) not null,
	tabela_destino varchar(250) not null,
	sql_insert_valida varchar(1000),
	sql_insert varchar(1000),
	sql_update_valida varchar(1000),
	sql_update varchar(1000),
	sql_delete_valida varchar(1000),
	sql_delete varchar(1000),
	id_tipo_conexao int not null,
	db_host varchar(250) not null,
	db_porta varchar(250) not null,
	db_usuario varchar(250) not null,
	db_senha varchar(250) not null,
	db_instancia varchar(250) not null,
	db_banco varchar(250) not null,
	identificador_tabela varchar(10),
	constraint pk_TB_CARGA_DESTINO primary key (id_carga_destino),
	constraint fk_TB_CARGA_DESTINO_TB_CARGA foreign key (id_carga) references TB_CARGA (id_carga),
	constraint fk_TB_CARGA_DESTINO_TB_TIPO_CONEXAO foreign key (id_tipo_conexao) references TB_TIPO_CONEXAO (id_tipo_conexao)
);
GO

CREATE TABLE TB_CARGA_DESTINO_CAMPO (
	id_carga_destino_campo int identity,
	id_carga_destino int not null,
	id_carga_campo int not null,
	is_pk bit not null constraint df_TB_CARGA_DESTINO_CAMPO_is_pk default 0,
	constraint pk_TB_CARGA_DESTINO_CAMPO primary key (id_carga_destino_campo),
	constraint fk_TB_CARGA_DESTINO_CAMPO_TB_CARGA_DESTINO foreign key (id_carga_destino) references TB_CARGA_DESTINO (id_carga_destino),
	constraint fk_TB_CARGA_DESTINO_CAMPO_TB_CARGA_CAMPO foreign key (id_carga_campo) references TB_CARGA_CAMPO (id_carga_campo)
);
GO