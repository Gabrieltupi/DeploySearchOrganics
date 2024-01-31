package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidarQuantidadeProdutos implements IValidarPedido {
    private final ProdutoRepository produtoRepository;

    @Override
    public void validar(Pedido pedido, Integer idUsuario, List<PedidoXProduto> produtos) throws Exception {
        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();
            BigDecimal estoque = produto.getQuantidade();
            Integer quantidadePedida = pedidoXProduto.getQuantidade();

            if (estoque.compareTo(new BigDecimal(quantidadePedida)) < 0) {
                throw new ValidacaoException("Estoque insuficiente para o produto: " + produto.getNome() + ", de id: " + produto.getIdProduto());
            }

        }

    }
}
