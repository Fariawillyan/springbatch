CREATE TABLE tb_campo (
   id_campo INT IDENTITY,
   campo VARCHAR(255) NOT NULL,
   id_tipo_campo INT NOT NULL,
   PRIMARY KEY (id_campo)
);

CREATE TABLE tb_carga (
   id_carga INT IDENTITY,
   descricao VARCHAR(255) NOT NULL,
   ems_numero INT NOT NULL,
   ftp_host VARCHAR(255) NOT NULL,
   ftp_path VARCHAR(255) NOT NULL,
   ftp_porta VARCHAR(255) NOT NULL,
   ftp_senha VARCHAR(255) NOT NULL,
   ftp_usuario VARCHAR(255) NOT NULL,
   identificador_tabela_posicao_final INT,
   identificador_tabela_posicao_inicial INT,
   interacao_posicao_final INT,
   interacao_posicao_inicial INT,
   nome_arquivo VARCHAR(255) NOT NULL,
   usa_identificador_tabela BIT NOT NULL, -- BOOLEAN convertido para BIT
   id_tipo_delimitador INT NOT NULL,
   PRIMARY KEY (id_carga)
);

CREATE TABLE tb_carga_campo (
   id_carga_campo INT IDENTITY,
   ordem INT NOT NULL,
   posicao_final INT,
   posicao_inicial INT,
   id_campo INT NOT NULL,
   id_carga INT NOT NULL,
   PRIMARY KEY (id_carga_campo)
);

CREATE TABLE tb_carga_destino (
   id_carga_destino INT IDENTITY,
   db_banco VARCHAR(255) NOT NULL,
   db_host VARCHAR(255) NOT NULL,
   db_instancia VARCHAR(255) NOT NULL,
   db_porta VARCHAR(255) NOT NULL,
   db_senha VARCHAR(255) NOT NULL,
   db_usuario VARCHAR(255) NOT NULL,
   descricao VARCHAR(255) NOT NULL,
   identificador_tabela VARCHAR(255),
   sql_delete VARCHAR(255),
   sql_insert VARCHAR(255),
   sql_update VARCHAR(255),
   tabela_destino VARCHAR(255) NOT NULL,
   id_carga INT NOT NULL,
   id_tipo_conexao INT NOT NULL,
   PRIMARY KEY (id_carga_destino)
);

CREATE TABLE tb_carga_destino_campo (
   id_carga_destino_campo INT IDENTITY,
   id_carga_campo INT NOT NULL,
   id_carga_destino INT NOT NULL,
   PRIMARY KEY (id_carga_destino_campo)
);

CREATE TABLE tb_tipo_campo (
   id_tipo_campo INT IDENTITY(1,1),
   descricao VARCHAR(255) NOT NULL,
   PRIMARY KEY (id_tipo_campo)
);

CREATE TABLE tb_tipo_conexao (
   id_tipo_conexao INT IDENTITY(1,1),
   descricao VARCHAR(255) NOT NULL,
   PRIMARY KEY (id_tipo_conexao)
);

CREATE TABLE tb_tipo_delimitador (
   id_tipo_delimitador INT IDENTITY,
   delimitador VARCHAR(255) NOT NULL,
   descricao VARCHAR(255) NOT NULL,
   usa_posicionamento BIT NOT NULL, -- BOOLEAN convertido para BIT
   PRIMARY KEY (id_tipo_delimitador)
);

-- Adicionar as foreign keys
ALTER TABLE tb_campo
ADD CONSTRAINT FK_campo_tipo_campo
FOREIGN KEY (id_tipo_campo) REFERENCES tb_tipo_campo (id_tipo_campo);

ALTER TABLE tb_carga
ADD CONSTRAINT FK_carga_tipo_delimitador
FOREIGN KEY (id_tipo_delimitador) REFERENCES tb_tipo_delimitador (id_tipo_delimitador);

ALTER TABLE tb_carga_campo
ADD CONSTRAINT FK_carga_campo_campo
FOREIGN KEY (id_campo) REFERENCES tb_campo (id_campo);

ALTER TABLE tb_carga_campo
ADD CONSTRAINT FK_carga_campo_carga
FOREIGN KEY (id_carga) REFERENCES tb_carga (id_carga);

ALTER TABLE tb_carga_destino
ADD CONSTRAINT FK_carga_destino_carga
FOREIGN KEY (id_carga) REFERENCES tb_carga (id_carga);

ALTER TABLE tb_carga_destino
ADD CONSTRAINT FK_carga_destino_tipo_conexao
FOREIGN KEY (id_tipo_conexao) REFERENCES tb_tipo_conexao (id_tipo_conexao);

ALTER TABLE tb_carga_destino_campo
ADD CONSTRAINT FK_carga_destino_campo_carga_campo
FOREIGN KEY (id_carga_campo) REFERENCES tb_carga_campo (id_carga_campo);

ALTER TABLE tb_carga_destino_campo
ADD CONSTRAINT FK_carga_destino_campo_carga_destino
FOREIGN KEY (id_carga_destino) REFERENCES tb_carga_destino (id_carga_destino);

---------------------- Teste --------------------------------

CREATE TABLE tb_pessoa (
   id INT IDENTITY,
   data_nascimento VARCHAR(255),
   email VARCHAR(255),
   idade INT,
   nome VARCHAR(255),
   PRIMARY KEY (id)
);