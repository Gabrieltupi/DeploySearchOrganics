--INSERINDO ADMIN--
INSERT INTO VEM_SER.Usuario (id_usuario, login, senha, cpf, ativo, nome, sobrenome, email, dataNascimento, id_endereco)
VALUES(seq_usuario.NEXTVAL, 'admin', 'admin', 'admin', 'T', 'Admin', 'Admin', 'admin@gmail.com', TO_DATE('01-01-1990', 'DD-MM-YYYY'), 1);


--INSERINDO USUARIO 1 COM ENDERECO--
-- dados USUARIO
INSERT INTO VEM_SER.Usuario (id_usuario, login, senha, cpf, ativo, nome, sobrenome, email, dataNascimento)
VALUES (seq_usuario.NEXTVAL, 'deyvidlucas', 'deyvid2024', '12345678901', 'T', 'Deyvid', 'Lucas', 'deivin@gmail.com', TO_DATE('01-01-2000', 'DD-MM-YYYY'));

-- dados ENDERECO
INSERT INTO VEM_SER.Endereco (id_endereco, id_usuario, logradouro, numero, complemento, cep, cidade, estado, pais)
VALUES (seq_endereco.NEXTVAL, (SELECT id_usuario FROM Usuario WHERE login = 'deyvidlucas'),
		'Rua Gaivota', '10', 'Apto 101', '04870-015', 'São Paulo', 'São Paulo', 'Brasil');
	
UPDATE VEM_SER.Usuario
SET id_endereco = 1
WHERE id_usuario = 1;

SELECT * FROM VEM_SER.EMPRESA e ;
--CADASTRANDO EMPRESA 1--
-- dados EMPRESA
INSERT INTO VEM_SER.Empresa (id_empresa, nomeFantasia, cnpj, razaoSocial, inscricaoEstadual, Campo)
VALUES (seq_empresa.NEXTVAL, 'Fazenda do tio Zé', '72351383000153', 'FazendaZezinho', '223233556', 'Alimenticio');

-- dados dos PRODUTOS
INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Batata', 'Batata organica', 2.0, 18, 2, 3.2, 'PC');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Brocolis', 'Brocolis de cabeça', 8.0, 18, 2, 3.2, 'KG');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Alface', 'Alface americana', 5.46, 18, 3, 3.2, 'PC');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Coentro', 'Coentro', 40.00, 10, 3, 3.2, 'KG');
    
INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Beterraba', 'Beterraba', 3.10, 18, 3, 3.2, 'KG');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Ovo', 'Caixa de Ovo branco', 8.50, 18, 4, 3.2, 'PC');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Arroz', 'Saco de 2kg de Arroz integral', 20, 18, 4, 3.2, 'KG');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Feijao Carioca', 'Saco de 2kg de Feijão', 15.50, 18, 4, 3.2, 'KG');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Leite', 'Leite', 4.05, 18, 5, 3.2, 'L');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Feijão', 'Saco de 1kg de Feijao preto', 12, 10, 4, 3.2, 'KG');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 3, 'Bergamota', 'Bergamota', 2.20, 18, 1, 3.2, 'KG');


--CADASTRANDO EMPRESA 2--
-- dados EMPRESA
INSERT INTO VEM_SER.Empresa (id_empresa, nomeFantasia, cnpj, razaoSocial, inscricaoEstadual, Campo)
VALUES (seq_empresa.NEXTVAL, 'Fazenda do tio João', '85324567000188', 'FazendaJoaozinho', '472589632', 'Agricultura Consciente');

-- dados dos PRODUTOS
INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 5, 'Milho', 'Espiga de milho', 2.99, 50, 1, 3.2, 'UN');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 5, 'Tomate', 'Tomate orgânico', 3.50, 30, 3, 3.2, 'KG');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 5, 'Pepino', 'Pepino', 1.75, 40, 3, 3.2, 'PC');

INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 5, 'Cenoura', 'Cenoura fresca', 2.25, 35, 3, 3.2, 'KG');
    
INSERT INTO VEM_SER.Produto (id_produto, id_empresa, nome, descricao, preço, quantidade_disponivel, tipo_categoria, taxa, unidade_medida)
VALUES(seq_produto.NEXTVAL, 5, 'Abóbora', 'Abóbora cabotiá', 6.80, 20, 3, 3.2, 'KG');


--ASSOCIANDO PRODUTOS A CATEGORIA--
INSERT INTO VEM_SER.CatalogoProduto (id_catalogo, id_produto, tipo_categoria, Nome, Descrição)
VALUES(seq_catalogo_produto.NEXTVAL, (SELECT id_produto FROM VEM_SER.Produto WHERE tipo_categoria = 1),  1, 'LEGUMES', 'Categoria de Legumes');

INSERT INTO VEM_SER.CatalogoProduto (id_catalogo, id_produto, tipo_categoria, Nome, Descrição)
VALUES(seq_catalogo_produto.NEXTVAL, (SELECT id_produto FROM VEM_SER.Produto WHERE tipo_categoria = 2), 2, 'VERDURAS_E_TEMPEROS', 'Categoria de Verduras e temperos');

INSERT INTO VEM_SER.CatalogoProduto (id_catalogo, id_produto, tipo_categoria, Nome, Descrição)
VALUES(seq_catalogo_produto.NEXTVAL, (SELECT id_produto FROM VEM_SER.Produto WHERE tipo_categoria = 3), 3, 'FRUTAS', 'Categoria de Frutas');

INSERT INTO VEM_SER.CatalogoProduto (id_catalogo, id_produto, tipo_categoria, Nome, Descrição)
VALUES(seq_catalogo_produto.NEXTVAL, (SELECT id_produto FROM VEM_SER.Produto WHERE tipo_categoria = 4), 4, 'OVOS', 'Categoria de Ovos');

INSERT INTO VEM_SER.CatalogoProduto (id_catalogo, id_produto, tipo_categoria, Nome, Descrição)
VALUES(seq_catalogo_produto.NEXTVAL, (SELECT id_produto FROM VEM_SER.Produto WHERE tipo_categoria = 5), 5, 'LEITES', 'Categoria de Leites');

INSERT INTO VEM_SER.CatalogoProduto (id_catalogo, id_produto, tipo_categoria, Nome, Descrição)
VALUES(seq_catalogo_produto.NEXTVAL, (SELECT id_produto FROM VEM_SER.Produto WHERE tipo_categoria = 6), 6, 'ARROZ_E_FEIJAO', 'Categoria de Arroz e Feijão');


-- INSERINDO CUPONS --
INSERT INTO VEM_SER.Cupom (id_cupom, nome_cupom, ativo, descricao, taxa_desconto)
VALUES(seq_cupom.NEXTVAL, 'SQUAD1EH10', 'F', 'Cupom de desconto de 10%', 10);

INSERT INTO VEM_SER.Cupom (id_cupom, nome_cupom, ativo, descricao, taxa_desconto)
VALUES(seq_cupom.NEXTVAL, 'SQUAD1EH20', 'T', 'Cupom de desconto de 20%', 20);

INSERT INTO VEM_SER.Cupom (id_cupom, nome_cupom, ativo, descricao, taxa_desconto)
VALUES(seq_cupom.NEXTVAL, 'SQUAD1EH30', 'T', 'Cupom de desconto de 30%', 30);


