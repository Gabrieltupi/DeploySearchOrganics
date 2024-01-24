package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ObjectMapper objectMapper) {
        this.produtoRepository = produtoRepository;
        this.objectMapper = objectMapper;
    }

    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produto) throws BancoDeDadosException {

        Produto produtoEntity= objectMapper.convertValue(produto, Produto.class);
        produtoEntity=produtoRepository.adicionar(produtoEntity);
        ProdutoDTO produtoDto = objectMapper.convertValue(produtoEntity, ProdutoDTO.class);

        return produtoDto;

    }

    public List<ProdutoDTO> list() throws BancoDeDadosException {

        List<Produto> produtos = produtoRepository.listar();
        return objectMapper.convertValue(produtos, new TypeReference<List<ProdutoDTO>>() {});
    }

    public void deleterProduto(Integer idEndereco) throws BancoDeDadosException {
        produtoRepository.remover(idEndereco);
    }


    public static ProdutoDTO buscarProdutoPorId(Integer id) throws BancoDeDadosException {
        Produto produto = produtoRepository.buscarProdutoPorId(id);
        return produto;
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








    public void listarProdutosPorCategoria(int categoria) {
        try {
            List<Produto> produtos = produtoRepository.listarProdutosPorCategoria(categoria);
            produtos.forEach(produto -> {

                System.out.println("-----------------");
            });
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao listar produtos por categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Produto> listarProdutosLoja(int idLoja) throws BancoDeDadosException {
        return produtoRepository.listarProdutosLoja(idLoja);
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


