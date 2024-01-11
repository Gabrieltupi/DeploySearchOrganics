-- sequences
CREATE SEQUENCE seq_pedido
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_carrinho
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_empresa
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_usuario
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_montagem_carrinho
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_endereco
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_catalogo_produto
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_ordem_pedido
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_cupom
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE seq_produto
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- Tabelas
CREATE TABLE Usuario
(
    id_usuario     INT PRIMARY KEY,
    id_endereco    INT,
    login          VARCHAR2(50) UNIQUE NOT NULL,
    senha          VARCHAR2(20) UNIQUE NOT NULL,
    cpf            CHAR(11) UNIQUE     NOT NULL,
    ativo          CHAR(1)             NOT NULL CHECK (ativo IN ('T', 'F')),
    nome           VARCHAR2(100)       NOT NULL,
    sobrenome      VARCHAR2(100)       NOT NULL,
    email          VARCHAR2(50)        NOT NULL,
    dataNascimento DATE                NOT NULL,
    CONSTRAINT FK_USUARIO_ENDERECO_ID FOREIGN KEY (id_endereco) REFERENCES Endereco (id_endereco) -- ALTER TABLE dps
);

CREATE TABLE Endereco
(
    id_endereco INT PRIMARY KEY,
    id_usuario  INT           NOT NULL,
    logradouro  VARCHAR2(100) NOT NULL,
    numero      VARCHAR2(10)  NOT NULL,
    complemento VARCHAR2(20)  NOT NULL,
    cep         CHAR(9)       NOT NULL,
    cidade      VARCHAR2(20)  NOT NULL,
    estado      VARCHAR2(20)  NOT NULL,
    pais        VARCHAR2(40)  NOT NULL,
    regiao      VARCHAR2(40),
    CONSTRAINT FK_ENDERECO_USUARIO_ID
        FOREIGN KEY (id_usuario)
            REFERENCES Usuario (id_usuario)
);

CREATE TABLE Empresa
(
    id_empresa        INT PRIMARY KEY,
    nomeFantasia      VARCHAR2(150)   NOT NULL,
    cnpj              CHAR(14) UNIQUE NOT NULL,
    razaoSocial       VARCHAR2(150)   NOT NULL,
    inscricaoEstadual CHAR(9),
    Campo             VARCHAR2(100)
);

CREATE TABLE Pedido
(
    id_pedido            INT PRIMARY KEY,
    id_usuario           INT        NOT NULL,
    id_montagem_carrinho INT        NOT NULL,
    id_endereco          INT        NOT NULL,
    id_cupom             INT,
    quantidade_pedida    NUMBER(10) NOT NULL,
    preço_total          DECIMAL    NOT NULL,
    forma_pagamento      VARCHAR2(255),
    status_pedido        VARCHAR2(25) CHECK (status_pedido IN
                                             ('AGUARDANDO_PAGAMENTO', 'CANCELADO', 'PAGO', 'EM_SEPARACAO', 'COLETADO',
                                              'A CAMINHO', 'ENTREGUE')),
    data_de_pedido       DATE       NOT NULL,
    tipo_entrega         DATE       NOT NULL,
    precoCarrinho        DECIMAL    NOT NULL,
    CONSTRAINT FK_PEDIDO_USUARIO_ID FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario),
    CONSTRAINT FK_PEDIDO_ENDERECO FOREIGN KEY (id_endereco) REFERENCES Endereco (id_endereco)
);

CREATE TABLE Carrinho
(
    id_carrinho INT PRIMARY KEY,
    id_usuario  INT NOT NULL,
    CONSTRAINT FK_CARRINHO_USUARIO_ID FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario)
);

CREATE TABLE MontagemCarrinho
(
    id_montagem_carrinho INT PRIMARY KEY,
    id_produto           INT        NOT NULL,
    id_carrinho          INT        NOT NULL,
    id_pedido            INT        NOT NULL,
    precoCarrinho        DECIMAL,
    quantidade           NUMBER(10) NOT NULL,
    CONSTRAINT FK_MONTAGEM_CARRINHO_ID FOREIGN KEY (id_carrinho) REFERENCES Carrinho (id_carrinho),
    CONSTRAINT FK_MONTAGEM_PEDIDO_ID FOREIGN KEY (id_pedido) REFERENCES Pedido (id_pedido)
);

CREATE TABLE CatalogoProduto
(
    id_catalogo    INT PRIMARY KEY,
    id_produto     INT           NOT NULL,
    tipo_categoria NUMBER(1) CHECK (tipo_categoria BETWEEN 1 AND 6),
    Nome           VARCHAR2(200),
    Descrição      VARCHAR2(255) NOT NULL
);

CREATE TABLE OrdemPedido
(
    id_ordem    INT PRIMARY KEY,
    id_pedido   INT        NOT NULL,
    id_produto  INT        NOT NULL,
    qtd_total   NUMBER(10) NOT NULL,
    preco_total DECIMAL    NOT NULL,
    CONSTRAINT FK_ORDEM_PEDIDO_ID FOREIGN KEY (id_pedido) REFERENCES Pedido (id_pedido)
);

CREATE TABLE Cupom
(
    id_cupom      INT PRIMARY KEY,
    nome_cupom    VARCHAR(50)  NOT NULL,
    ativo         CHAR(1)      NOT NULL CHECK (ativo IN ('T', 'F')),
    descricao     VARCHAR(255) NOT NULL,
    taxa_desconto DECIMAL      NOT NULL
);

CREATE TABLE Produto
(
    id_produto            INT PRIMARY KEY,
    id_empresa            INT           NOT NULL,
    nome                  VARCHAR2(200) NOT NULL,
    descricao             VARCHAR2(255) NOT NULL,
    preço                 DECIMAL       NOT NULL,
    quantidade_disponivel NUMBER(10)    NOT NULL,
    tipo_categoria        NUMBER(1) CHECK (tipo_categoria BETWEEN 1 AND 6),
    taxa                  NUMBER(6, 2),
    unidade_medida        CHAR(15),
    CONSTRAINT FK_PRODUTO_EMPRESA_ID
        FOREIGN KEY (id_empresa)
            REFERENCES Empresa (id_empresa)
);

ALTER TABLE Usuario
    ADD CONSTRAINT FK_USUARIO_ENDERECO_ID FOREIGN KEY (id_endereco) REFERENCES Endereco (id_endereco);

ALTER TABLE Pedido
    ADD CONSTRAINT FK_PEDIDO_MONTAGEM_ID FOREIGN KEY (id_montagem_carrinho) REFERENCES MontagemCarrinho (id_montagem_carrinho);

ALTER TABLE Pedido
    ADD CONSTRAINT FK_PEDIDO_CUPOM_ID FOREIGN KEY (id_cupom) REFERENCES Cupom (id_cupom);

ALTER TABLE MontagemCarrinho
    ADD CONSTRAINT FK_MONTAGEM_PRODUTO_ID FOREIGN KEY (id_produto) REFERENCES Produto (id_produto);

ALTER TABLE CatalogoProduto
    ADD CONSTRAINT FK_CATALOGO_PRODUTO_ID FOREIGN KEY (id_produto) REFERENCES Produto (id_produto);

ALTER TABLE OrdemPedido
    ADD CONSTRAINT FK_ORDEM_PRODUTO_ID FOREIGN KEY (id_produto) REFERENCES Produto (id_produto);