package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void adicionarProduto(Produto produto) {
        try {
            produtoRepository.adicionar(produto);
            System.out.println("Produto adicionado com sucesso!");
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listarProdutos() {
        try {
            List<Produto> listar = produtoRepository.listar();
            listar.forEach(Produto::imprimir);
        } catch (BancoDeDadosException e) {
            System.out.println(" Erro ao listar produtos" + e.getMessage());
            e.printStackTrace();
        }
    }


    public void atualizarProduto(int id, Produto produtos) {
        try {
            produtoRepository.editar(id, produtos);
            System.out.println("Produto atualizado");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletarProduto(Integer id) {
        try {
            boolean produtoDeletado = produtoRepository.remover(id);
            System.out.println("Produto removido com sucesso");
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao deletar produto" + e.getMessage());
        }
    }


    public Produto buscarProdutoPorId(int id) {
        try {
            Produto produto = produtoRepository.buscarProdutoPorId(id);
            if (produto != null) {
                System.out.println("Produto encontrado:");
                produto.imprimir();
                System.out.println("-----------------");
            } else {
                System.out.println("Produto com ID " + id + " não encontrado");
            }
            return produto;
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao buscar produto por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void listarProdutosPorCategoria(int categoria) {
        try {
            List<Produto> produtos = produtoRepository.listarProdutosPorCategoria(categoria);
            produtos.forEach(produto -> {
                produto.imprimir();
                System.out.println("-----------------");
            });
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao listar produtos por categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listarProdutosLoja(int idLoja) {
        try {
            List<Produto> produtos = produtoRepository.listarProdutosLoja(idLoja);
            produtos.forEach(produto -> {
                System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " quantidade: " + produto.getQuantidade());
                System.out.println("Descrição: " + produto.getDescricao());
                System.out.println();
            });
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao listar produtos da loja: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Produto> buscarProdutos() {
        try {
            return produtoRepository.listar();
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void atualizarSilenciosamente(Produto produto) {
        try {
            produtoRepository.editar(produto.getIdProduto(), produto);

        } catch (SQLException e) {
            System.out.println("Erro " + e.getMessage());
            e.printStackTrace();
        }
    }
}
