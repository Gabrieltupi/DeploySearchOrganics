package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockProduto {
    public static List<ProdutoDTO> retornarProdutosDTOs(List<Produto> produtos){
        List<ProdutoDTO> produtoDTOS =  produtos.stream().map(produto -> {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setQuantidade(produto.getQuantidade());
            produtoDTO.setUrlImagem(produto.getUrlImagem());
            produtoDTO.setIdEmpresa(produto.getIdEmpresa());
            produtoDTO.setCategoria(produto.getCategoria());
            produtoDTO.setDescricao(produto.getDescricao());
            produtoDTO.setPreco(produto.getPreco());
            produtoDTO.setTipoAtivo(produto.getTipoAtivo());
            produtoDTO.setUnidadeMedida(produto.getUnidadeMedida());
            produtoDTO.setTaxa(produto.getTaxa());

            return produtoDTO;
        }).toList();

        return produtoDTOS;
    }

    public static List<Produto> criarProdutos(Integer idEmpresa){
        List<Produto> produtos = new ArrayList<>();
        Produto produto = new Produto();
        produto.setIdProduto(new Random().nextInt());
        produto.setQuantidade(BigDecimal.valueOf(5));
        produto.setUrlImagem("1.jpg");
        produto.setIdEmpresa(idEmpresa);
        produto.setCategoria(TipoCategoria.FRUTAS);
        produto.setDescricao("Produto 1");
        produto.setPreco(BigDecimal.valueOf(10));
        produto.setTipoAtivo(TipoAtivo.S);
        produto.setUnidadeMedida(UnidadeMedida.UN);
        produto.setTaxa(1d);
        produtos.add(produto);

        return produtos;
    }

}
