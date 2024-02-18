// Cria banco de dados
use search_organic



// Cria coleção
db.createCollection("produto")



// Insere registros
db.produto.insert(
    {
        "id_produto" : 1, 
        "id_empresa" : 2,
        "nome" : "Soja",
        "descricao" : "Soja para ração animal",
        "preco" : 10,
        "tipo_categoria" : 1,
        "taxa" : 2,
        "unidade_medida" : "KG",
        "url_imagem" : "url_soja.jpg",
        "ativo" : "S",
        "quantidade" : 32,
    }
)

db.produto.insert(
    {
        "id_produto" : 2, 
        "id_empresa" : 4,
        "nome" : "Milho",
        "descricao" : "Milho para ração animal",
        "preco" : 8,
        "tipo_categoria" : 2,
        "taxa" : 3,
        "unidade_medida" : "KG",
        "url_imagem" : "url_milho.jpg",
        "ativo" : "S",
        "quantidade" : 64,
    }
)

db.produto.insert(
    {
        "id_produto" : 3, 
        "id_empresa" : 3,
        "nome" : "Carne de frango",
        "descricao" : "Carne de frango resfriada",
        "preco" : 24,
        "tipo_categoria" : 3,
        "taxa" : 4,
        "unidade_medida" : "KG",
        "url_imagem" : "url_carne_frango.jpg",
        "ativo" : "N",
        "quantidade" : 8,
    }
)

db.produto.insert(
    {
        "id_produto" : 4, 
        "id_empresa" : 3,
        "nome" : "Alface",
        "descricao" : "Alface hidropônica",
        "preco" : 2,
        "tipo_categoria" : 4,
        "taxa" : 2,
        "unidade_medida" : "UN",
        "url_imagem" : "url_alface.jpg",
        "ativo" : "S",
        "quantidade" : 15,
    }
)



// Busca registros
// Lista todos os produtos
db.produto.find().pretty()

// Lista todos os produtos de uma empresa
db.produto.find({ id_empresa: 3 }).pretty()

// Lista todos os produtos ativos
db.produto.find({ ativo: "S" }).pretty()

// Lista todos os produtos ativos de uma empresa
db.produto.find({ ativo: "S", id_empresa: 3 }).pretty()

// Lista todos os produtos com nome Milho ou Soja
db.produto.find({
    $or : [
        {"nome" : "Milho"},
        {"nome" : "Soja"}    
    ]
})
