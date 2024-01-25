--INSERINDO ADMIN--
INSERT INTO Usuario (id_usuario,
                     login,
                     senha,
                     cpf,
                     ativo,
                     nome,
                     sobrenome,
                     email,
                     dataNascimento)
VALUES (seq_usuario.NEXTVAL,
        'admin',
        'admin',
        'admin',
        'T',
        'Admin',
        'Admin',
        'admin@gmail.com',
        TO_DATE('01-01-1990', 'DD-MM-YYYY'));

--INSERINDO USUARIO 1 COM ENDERECO--
-- dados USUARIO
INSERT INTO Usuario (id_usuario,
                     login,
                     senha,
                     cpf,
                     ativo,
                     nome,
                     sobrenome,
                     email,
                     dataNascimento)
VALUES (seq_usuario.NEXTVAL,
        'deyvidlucas',
        'dey2024',
        '12345678901',
        'T',
        'Deyvid',
        'Lucas',
        'deivin@gmail.com',
        TO_DATE('01-01-2000', 'DD-MM-YYYY'));

-- dados ENDERECO
INSERT INTO Endereco (id_endereco,
                      id_usuario,
                      logradouro,
                      numero,
                      complemento,
                      cep,
                      cidade,
                      estado,
                      pais)
VALUES (seq_endereco.NEXTVAL,
        (SELECT id_usuario
         FROM Usuario
         WHERE login = 'deyvidlucas'),
        'Rua Gaivota',
        '10',
        'Apto 101',
        '04870-015',
        'São Paulo',
        'São Paulo',
        'Brasil');

UPDATE
    Usuario
SET id_endereco = 1
WHERE id_usuario = 2;

select * from EMPRESA;

--CADASTRANDO EMPRESA 1--
-- dados EMPRESA
INSERT INTO Empresa (id_empresa,
                     id_usuario,
                     nomeFantasia,
                     cnpj,
                     razaoSocial,
                     inscricaoEstadual,
                     setor)
VALUES (seq_empresa.NEXTVAL,
        3,
        'Fazenda do tio Zé',
        '72351383000153',
        'FazendaZezinho',
        '223233556',
        'Alimenticio');

-- dados dos PRODUTOS
INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        3,
        'Batata',
        'Batata organica',
        2.0,
        18,
        2,
        3.2,
        'PC');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Brocolis',
        'Brocolis de cabeça',
        8.0,
        18,
        2,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Alface',
        'Alface americana',
        5.46,
        18,
        3,
        3.2,
        'PC');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Coentro',
        'Coentro',
        40.00,
        10,
        3,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Beterraba',
        'Beterraba',
        3.10,
        18,
        3,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Ovo',
        'Caixa de Ovo branco',
        8.50,
        18,
        4,
        3.2,
        'PC');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Arroz',
        'Saco de 2kg de Arroz integral',
        20,
        18,
        4,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Feijao Carioca',
        'Saco de 2kg de Feijão',
        15.50,
        18,
        4,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Leite',
        'Leite',
        4.05,
        18,
        5,
        3.2,
        'L');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Feijão',
        'Saco de 1kg de Feijao preto',
        12,
        10,
        4,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        1,
        'Bergamota',
        'Bergamota',
        2.20,
        18,
        1,
        3.2,
        'KG');

--CADASTRANDO EMPRESA 2--
-- dados EMPRESA
INSERT INTO Empresa (id_empresa,
                     id_usuario,
                     nomeFantasia,
                     cnpj,
                     razaoSocial,
                     inscricaoEstadual,
                     setor)
VALUES (seq_empresa.NEXTVAL,
        3,
        'Fazenda do tio João',
        '85324567000188',
        'FazendaJoaozinho',
        '472589632',
        'Agricultura Consciente');

-- dados dos PRODUTOS
INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        2,
        'Milho',
        'Espiga de milho',
        2.99,
        50,
        1,
        3.2,
        'UN');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        2,
        'Tomate',
        'Tomate orgânico',
        3.50,
        30,
        3,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        2,
        'Pepino',
        'Pepino',
        1.75,
        40,
        3,
        3.2,
        'PC');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        2,
        'Cenoura',
        'Cenoura fresca',
        2.25,
        35,
        3,
        3.2,
        'KG');

INSERT INTO Produto (id_produto,
                     id_empresa,
                     nome,
                     descricao,
                     PRECO,
                     quantidade_disponivel,
                     TIPO_CATEGORIA,
                     taxa,
                     unidade_medida)
VALUES (seq_produto.NEXTVAL,
        2,
        'Abóbora',
        'Abóbora cabotiá',
        6.80,
        20,
        3,
        3.2,
        'KG');

-- INSERINDO CUPONS --
INSERT INTO Cupom (id_cupom,
                   ID_EMPRESA,
                   nome_cupom,
                   ativo,
                   descricao,
                   taxa_desconto)
VALUES (seq_cupom.NEXTVAL,
        'SQUAD1EH10',
        1,
        'F',
        'Cupom de desconto de 10%',
        10);

INSERT INTO Cupom (id_cupom,
                   ID_EMPRESA,
                   nome_cupom,
                   ativo,
                   descricao,
                   taxa_desconto)
VALUES (seq_cupom.NEXTVAL,
        'SQUAD1EH20',
        1,
        'F',
        'Cupom de desconto de 20%',
        20);

INSERT INTO Cupom (id_cupom,
                   ID_EMPRESA,
                   nome_cupom,
                   ativo,
                   descricao,
                   taxa_desconto)
VALUES (seq_cupom.NEXTVAL,
        'SQUAD1EH30',
        1,
        'F',
        'Cupom de desconto de 30%',
        30);

INSERT INTO Pedido(
        id_pedido,
        id_usuario,
        id_endereco,
        id_cupom,
        preco_frete,
        forma_pagamento,
        status_pedido,
        data_de_pedido,
        data_entrega,
        preco_carrinho
        )
VALUES(
        seq_pedido.NEXTVAL,
        2,
        1,
        1,
        10.00,
        'PIX',
        'PAGO',
        TO_DATE('01-01-2024', 'DD-MM-YYYY'),
        TO_DATE('08-01-2024', 'DD-MM-YYYY'),
        100.00
        );

INSERT INTO Pedido(
        id_pedido,
        id_usuario,
        id_endereco,
        id_cupom,
        preco_frete,
        forma_pagamento,
        status_pedido,
        data_de_pedido,
        data_entrega,
        preco_carrinho
        )
VALUES(
        seq_pedido.NEXTVAL,
        2,
        1,
        2,
        10.00,
        'PIX',
        'PAGO',
        TO_DATE('12-10-2023', 'DD-MM-YYYY'),
        TO_DATE('14-10-2023', 'DD-MM-YYYY'),
        150.00
        );

INSERT INTO OrdemPedido(
        id_ordem,
        id_pedido,
        qtd_total,
        preco_total
        )
VALUES(
        seq_ordem_pedido.NEXTVAL,
        2,
        2,
        160.00
        );

INSERT INTO OrdemPedido(
        id_ordem,
        id_pedido,
        qtd_total,
        preco_total
        )
VALUES(
        seq_ordem_pedido.NEXTVAL,
        1,
        2,
        110.00
        );

