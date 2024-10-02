SET IDENTITY_INSERT TB_TIPO_DELIMITADOR ON;

INSERT INTO TB_TIPO_DELIMITADOR (id_tipo_delimitador, descricao, delimitador, usa_posicionamento)
VALUES (3, 'TESTE', '|', 0);

SET IDENTITY_INSERT TB_TIPO_DELIMITADOR OFF;

go

SET IDENTITY_INSERT TB_TIPO_CAMPO ON;

INSERT INTO TB_TIPO_CAMPO (id_tipo_campo, descricao)
VALUES (1, 'String'),
       (2, 'Data'),
       (3, 'int');

SET IDENTITY_INSERT TB_TIPO_CAMPO OFF;

go

SET IDENTITY_INSERT TB_CAMPO ON;

INSERT INTO TB_CAMPO (id_campo, campo, id_tipo_campo)
VALUES (1, 'NOME', 1),
       (2, 'EMAIL', 1),
       (3, 'DATA_NASCIMENTO', 2),
       (4, 'IDADE', 2),
       (5, 'ID', 3);

SET IDENTITY_INSERT TB_CAMPO OFF;

go

SET IDENTITY_INSERT TB_CARGA ON;

INSERT INTO TB_CARGA (id_carga, descricao, ems_numero, id_tipo_delimitador, nome_arquivo, ftp_host,
                      ftp_porta, ftp_usuario, ftp_senha, ftp_path, interacao_posicao_inicial,
                      interacao_posicao_final, usa_identificador_tabela, identificador_tabela_posicao_inicial,
                      identificador_tabela_posicao_final)
VALUES (3, 'Carga 1', 2, 3, 'TESTE_PIPE.crg', '127.0.0.1', '2323', 'local', '123', '/CargaMaster/001/',
        1, 1, 0, 1, 2);

SET IDENTITY_INSERT TB_CARGA OFF;

go

SET IDENTITY_INSERT TB_CARGA_CAMPO ON;

INSERT INTO TB_CARGA_CAMPO (id_carga_campo, id_carga, id_campo, ordem, posicao_inicial, posicao_final)
VALUES (1, 3, 1, 1, -1, -1),
       (2, 3, 2, 2, -1, -1),
       (3, 3, 3, 3, -1, -1),
       (4, 3, 4, 4, -1, -1),
       (5, 3, 5, 5, -1, -1);

SET IDENTITY_INSERT TB_CARGA_CAMPO OFF;

go

SET IDENTITY_INSERT TB_TIPO_CONEXAO ON;

INSERT INTO TB_TIPO_CONEXAO (id_tipo_conexao, descricao)
VALUES (3, 'Operadora1');

SET IDENTITY_INSERT TB_TIPO_CONEXAO OFF;

go

SET IDENTITY_INSERT TB_CARGA_DESTINO ON;

INSERT INTO TB_CARGA_DESTINO (id_carga_destino, id_carga, descricao, tabela_destino, sql_insert, sql_update,
                               sql_delete, id_tipo_conexao, db_host, db_porta, db_usuario, db_senha,
                               db_instancia, db_banco, identificador_tabela)
VALUES (3, 3, 'Cadastro de pessoas', 'TB_PESSOA',
        'INSERT INTO TB_PESSOA (nome, email, data_nascimento, idade) VALUES (:nome, :email, :dataNascimento, :idade)',
        'TESTE', 'TESTE',
        3, '1', '1', '1', 'Teste', 'teste123', 'SQLSERVER', 'TESTE_1');

SET IDENTITY_INSERT TB_CARGA_DESTINO OFF;

go

SET IDENTITY_INSERT TB_CARGA_DESTINO_CAMPO ON;

INSERT INTO TB_CARGA_DESTINO_CAMPO (id_carga_destino_campo, id_carga_destino, id_carga_campo)
VALUES (3, 3, 3);

SET IDENTITY_INSERT TB_CARGA_DESTINO_CAMPO OFF;
