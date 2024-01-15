CREATE SEQUENCE seq_pedido START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE seq_pedidoxprodutos START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE seq_empresa START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE seq_usuario START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE seq_endereco START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE seq_ordem_pedido START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE seq_cupom START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE seq_produto START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE TABLE PedidoXProduto (
    id_pedido INT,
    id_produto INT,
    quantidade NUMBER(10) NOT NULL
);
CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY,
    login VARCHAR2(50) UNIQUE NOT NULL,
    senha VARCHAR2(255) NOT NULL,
    cpf CHAR(11) UNIQUE NOT NULL,
    ativo CHAR(1) CHECK (ativo IN ('S', 'N')),
    nome VARCHAR2(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    dataNascimento DATE NOT NULL
);

CREATE TABLE Endereco (
    id_endereco INT PRIMARY KEY,
    id_usuario INT NOT NULL,
    logradouro VARCHAR2(100) NOT NULL,
    numero VARCHAR2(10) NOT NULL,
    complemento VARCHAR2(20) NOT NULL,
    cep CHAR(9) NOT NULL,
    cidade VARCHAR2(20) NOT NULL,
    estado VARCHAR2(20) NOT NULL,
    pais VARCHAR2(40) NOT NULL,
    regiao VARCHAR2(40),
    CONSTRAINT FK_ENDERECO_USUARIO_ID FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario)
);

CREATE TABLE Empresa (
    id_empresa INT PRIMARY KEY NOT NULL,
    id_usuario INT NOT NULL,
    nomeFantasia VARCHAR2(150) NOT NULL,
    cnpj CHAR(14) UNIQUE NOT NULL,
    razaoSocial VARCHAR2(150) NOT NULL,
    inscricaoEstadual CHAR(9),
    setor VARCHAR2(100),
    CONSTRAINT FK_EMPRESA_USUARIO_ID FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario)
);
CREATE TABLE Cupom (
    id_cupom INT PRIMARY KEY,
    nome_cupom VARCHAR(50) NOT NULL,
    ativo CHAR(1) CHECK (ativo IN ('S', 'N')),
    descricao VARCHAR(255) NOT NULL,
    taxa_desconto DECIMAL NOT NULL,
    id_empresa INT NOT NULL,
    CONSTRAINT FK_CUPOM_EMPRESA_ID FOREIGN KEY (id_empresa) REFERENCES Empresa (id_empresa)
);

CREATE TABLE Produto (
    id_produto INT PRIMARY KEY NOT NULL,
    id_empresa INT NOT NULL,
    nome VARCHAR2(200) NOT NULL,
    descricao VARCHAR2(255) NOT NULL,
    preco DECIMAL NOT NULL,
    quantidade_disponivel NUMBER(10) NOT NULL,
    tipo_categoria NUMBER(1) CHECK (
        tipo_categoria BETWEEN 1
        AND 6
    ),
    taxa NUMBER(6, 2),
    unidade_medida CHAR(15)
);




CREATE TABLE Pedido (
    id_pedido INT PRIMARY KEY NOT NULL,
    id_usuario INT NOT NULL,
    id_endereco INT NOT NULL,
    id_cupom INT,
    preco_frete DECIMAL NOT NULL,
    forma_pagamento VARCHAR2(255) CHECK(
        forma_pagamento IN ('PIX', 'CREDITO', 'DEBITO')
    ),
    status_pedido VARCHAR2(25) CHECK (
        status_pedido IN (
            'AGUARDANDO_PAGAMENTO',
            'CANCELADO',
            'PAGO',
            'EM_SEPARACAO',
            'COLETADO',
            'A CAMINHO',
            'ENTREGUE'
        )
    ),
    data_de_pedido DATE NOT NULL,
    data_entrega DATE NOT NULL,
    preco_carrinho DECIMAL NOT NULL
);



CREATE TABLE OrdemPedido (
    id_ordem INT PRIMARY KEY NOT NULL,
    id_pedido INT NOT NULL,
    qtd_total NUMBER(10) NOT NULL,
    preco_total DECIMAL NOT NULL,
    CONSTRAINT FK_ORDEM_PEDIDO_PEDIDO_ID FOREIGN KEY (id_pedido) REFERENCES Pedido (id_pedido)
);



ALTER TABLE
    Pedido
ADD
    CONSTRAINT FK_PEDIDO_CUPOM_ID FOREIGN KEY (id_cupom) REFERENCES Cupom (id_cupom);

ALTER TABLE
    OrdemPedido
ADD
    CONSTRAINT FK_ORDEM_PEDIDO_PEDIDO_ID FOREIGN KEY (id_pedido) REFERENCES Pedido (id_pedido);

ALTER TABLE
    PedidoxProduto
ADD
    CONSTRAINT FK_PEDIDO_PRODUTOS_PEDIDO_ID FOREIGN KEY (id_pedido) REFERENCES Pedido (id_pedido);

ALTER TABLE
    PedidoxProduto
ADD
    CONSTRAINT FK_PEDIDO_PRODUTOS_PRODUTO_ID FOREIGN KEY (id_produto) REFERENCES Produto (id_produto);

ALTER TABLE
    PedidoXProduto
ADD
    CONSTRAINT PK_PEDIDO_PRODUTO PRIMARY KEY (id_pedido, id_produto);

ALTER TABLE
    Pedido
ADD
    CONSTRAINT FK_PEDIDO_CUPOM_ID FOREIGN KEY (id_cupom) REFERENCES Cupom (id_cupom);

ALTER TABLE
    Pedido
ADD
    CONSTRAINT FK_PEDIDO_USUARIO_ID FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario);

ALTER TABLE
    Pedido
ADD
    CONSTRAINT FK_PEDIDO_ENDERECO_ID FOREIGN KEY (id_endereco) REFERENCES Endereco (id_endereco);
