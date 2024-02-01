package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;


    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produto) throws Exception {
        Produto produtoEntity = objectMapper.convertValue(produto, Produto.class);
        produtoEntity = produtoRepository.adicionar(produtoEntity);
        ProdutoDTO produtoDto = objectMapper.convertValue(produtoEntity, ProdutoDTO.class);

        return produtoDto;

    }

    public ProdutoDTO atualizarProduto(Integer id, @Valid ProdutoUpdateDTO produtos) throws Exception {
        log.debug("testando id nulo");
        Produto produtoEntity = objectMapper.convertValue(produtos, Produto.class);
        produtoEntity.setIdProduto(id);

        produtoRepository.editar(id, produtoEntity);
        return null;
    }

    public List<ProdutoDTO> list() throws BancoDeDadosException {

        List<Produto> produtos = produtoRepository.listar();
        return objectMapper.convertValue(produtos, new TypeReference<List<ProdutoDTO>>() {
        });
    }

    public void deletarProduto(Integer idProduto) throws Exception {
        try {
            if (produtoRepository.remover(idProduto)) {
                return;
            }
            throw new RegraDeNegocioException("Produto não encontrado");
        } catch (Exception e) {
            throw new Exception("Erro ao remover o usuário: " + e.getMessage(), e);
        }
    }


    public Produto buscarProdutoPorId(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        Produto produto = produtoRepository.buscarProdutoPorId(id);
        if (produto == null) {
            throw new RegraDeNegocioException("O produto com o ID " + id + " não foi encontrado.");
        }
        return produto;
    }

    public List<Produto> listarProdutosPorCategoria(Integer categoria) throws BancoDeDadosException, RegraDeNegocioException {

        if (categoria == null) {
            throw new RegraDeNegocioException("A categoria com o ID não foi encontrado.");
        }
        List<Produto> produtos = produtoRepository.listarProdutosPorCategoria(categoria);

        if (produtos.isEmpty()) {
            throw new RegraDeNegocioException("Não há produtos disponíveis para a categoria fornecida.");
        }
        return produtos;
    }


    public List<Produto> listarProdutosLoja(Integer idLoja) throws BancoDeDadosException, RegraDeNegocioException {
        return produtoRepository.listarProdutosLoja(idLoja);
    }

    public String getMensagemProdutoEmail(List<PedidoXProduto> produtos) {
        StringBuilder mensagemFinal = new StringBuilder();
        for (PedidoXProduto pedidoXProduto : produtos) {
            String mensagemProduto = String.format("""
                            Nome: %s, Quantidade:  %s, Valor por cada quantidade: R$ %s  <br>
                            """, pedidoXProduto.getProduto().getNome(),
                    pedidoXProduto.getQuantidade(),
                    pedidoXProduto.getProduto().getPreco());
            mensagemFinal.append(mensagemProduto);
        }
        return mensagemFinal.toString();
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



