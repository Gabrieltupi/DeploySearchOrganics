package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ValidarPrecoTotal implements IValidarPedido {

    @Override
    public void validar(Pedido pedido, Integer idUsuario) throws Exception {
        BigDecimal precoCarrinhoTotal = new BigDecimal(0);

        for (ProdutoCarrinho produtoCarrinho : pedido.getProdutos()) {
            Produto produto = produtoCarrinho.getProduto();
            precoCarrinhoTotal = precoCarrinhoTotal.add(produto.getPreco());
        }

        if (pedido.getCupom() != null) {
            BigDecimal taxaDeDesconto = pedido.getCupom().getTaxaDeDesconto();

            BigDecimal desconto = precoCarrinhoTotal.multiply(taxaDeDesconto.divide(new BigDecimal(100)));

            precoCarrinhoTotal = precoCarrinhoTotal.subtract(desconto);

        }

        pedido.setPrecoCarrinho(precoCarrinhoTotal);

    }
}
