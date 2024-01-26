package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ValidarQuantidadeProdutos implements  IValidarPedido{
    private final ProdutoRepository produtoRepository;
    @Override
    public void validar(PedidoCreateDTO pedidoCreateDTO, Integer idUsuario) throws Exception {
        for(ProdutoCarrinho produtoCarrinho : pedidoCreateDTO.getProdutos()){
            Produto produto = produtoRepository.buscarProdutoPorId(produtoCarrinho.getIdProduto());
            BigDecimal estoque  = produto.getQuantidade();
            Integer quantidadePedida = produtoCarrinho.getQuantidade();

            if(estoque.compareTo(new BigDecimal(quantidadePedida)) < 0){
                throw new ValidacaoException("Estoque insuficiente para o produto: " + produto.getNome() + ", de id: " + produto.getIdProduto());
            }

        }

    }
}
