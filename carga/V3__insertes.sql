USE CargaMaster
GO

INSERT INTO TB_CAMPO (campo, id_tipo_campo) values ('EMS_NUMERO', 2);
INSERT INTO TB_CAMPO (campo, id_tipo_campo) values ('USUARIO_NUMERO', 2);
INSERT INTO TB_CAMPO (campo, id_tipo_campo) values ('USUARIO_NOME', 1);
INSERT INTO TB_CAMPO (campo, id_tipo_campo) values ('DATA_NASCIMENTO', 5);
INSERT INTO TB_CAMPO (campo, id_tipo_campo) values ('PLANO_NUMERO', 2);
INSERT INTO TB_CAMPO (campo, id_tipo_campo) values ('PLANO_DESCRICAO', 1);
GO

INSERT INTO TB_CARGA (descricao,ems_numero,id_tipo_delimitador,nome_arquivo,ftp_host,ftp_porta,ftp_usuario,ftp_senha,ftp_path,interacao_posicao_inicial,interacao_posicao_final)
VALUES ('Carga 1',001,3,'TESTE_PIPE.crg','127.0.0.1',2323,'local','123','/CargaMaster/001/',1,2);
INSERT INTO TB_CARGA (descricao,ems_numero,id_tipo_delimitador,nome_arquivo,ftp_host,ftp_porta,ftp_usuario,ftp_senha,ftp_path,interacao_posicao_inicial,interacao_posicao_final)
VALUES ('Carga 2',002,2,'TESTE_PONTO_VIRGULA.crg','127.0.0.1',2323,'local','123','/CargaMaster/002/',1,2);
INSERT INTO TB_CARGA (descricao,ems_numero,id_tipo_delimitador,nome_arquivo,ftp_host,ftp_porta,ftp_usuario,ftp_senha,ftp_path,interacao_posicao_inicial,interacao_posicao_final)
VALUES ('Carga 3',003,1,'TESTE_POSICAO.crg','127.0.0.1',2323,'local','123','/CargaMaster/003/',1,2);
GO

--PIPE
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (1, 1, 1);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (1, 2, 2);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (1, 3, 3);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (1, 5, 4);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (1, 6, 5);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (1, 4, 6);
GO

--PontoVirgula
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (2, 1, 1);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (2, 2, 2);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (2, 3, 3);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (2, 5, 4);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (2, 6, 5);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem) values (2, 4, 6);
GO


--Posicao
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem, posicao_inicial, posicao_final) values (3, 1, 1, 2, 5);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem, posicao_inicial, posicao_final) values (3, 2, 2, 5, 20);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem, posicao_inicial, posicao_final) values (3, 3, 3, 20, 80);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem, posicao_inicial, posicao_final) values (3, 5, 4, 80, 90);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem, posicao_inicial, posicao_final) values (3, 6, 5, 90, 130);
INSERT INTO tb_carga_campo (id_carga, id_campo, ordem, posicao_inicial, posicao_final) values (3, 4, 6, 130, 140);
GO

insert into tb_carga_destino (id_carga, descricao, tabela_destino, sql_insert, sql_update, sql_delete, id_tipo_conexao, db_host, db_porta, db_usuario, db_senha, db_instancia, db_banco, identificador_tabela)
values (1, 'Tabela de usarios no DB1', 'TB_USUARIO', 'INSERT INTO TB_USUARIO (numero, nome, nascimento) VALUES (:USUARIO_NUMERO, :USUARIO_NOME, :DATA_NASCIMENTO)',
       '', '', 1, 'DESKTOP-T68RIAC', 0, 'Teste', 'teste123', 'SQLSERVER', 'TESTE_1', NULL);

insert into tb_carga_destino (id_carga, descricao, tabela_destino, sql_insert, sql_update, sql_delete, id_tipo_conexao, db_host, db_porta, db_usuario, db_senha, db_instancia, db_banco, identificador_tabela)
values (2, 'Tabela de usarios no DB2', 'TB_CLIENTE', 'INSERT INTO TB_CLIENTE (emp_numero, cartao, nome, dt_nasc) VALUES (:EMS_NUMERO, :USUARIO_NUMERO, :USUARIO_NOME, :DATA_NASCIMENTO)',
       '', '', 1, 'DESKTOP-T68RIAC', 0, 'Teste', 'teste123', 'SQLSERVER', 'TESTE_2', NULL);

insert into tb_carga_destino (id_carga, descricao, tabela_destino, sql_insert, sql_update, sql_delete, id_tipo_conexao, db_host, db_porta, db_usuario, db_senha, db_instancia, db_banco, identificador_tabela)
values (3, 'Tabela de usarios no DB3', 'TB_USU_CLI', 'INSERT INTO TB_USU_CLI (num_cartao, nome, data_nascimento, id_plano) VALUES (:USUARIO_NUMERO, :USUARIO_NOME, :DATA_NASCIMENTO, :PLANO_NUMERO)',
       '', '', 1, 'DESKTOP-T68RIAC', 0, 'Teste', 'teste123', 'SQLSERVER', 'TESTE_3', NULL);
GO

insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (1, 2);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (1, 3);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (1, 4);

insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (2, 7);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (2, 8);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (2, 9);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (2, 12);

insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (3, 14);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (3, 15);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (3, 18);
insert into TB_CARGA_DESTINO_CAMPO (id_carga_destino, id_carga_campo) VALUES (3, 16);
GO

