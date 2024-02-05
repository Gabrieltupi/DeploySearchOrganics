package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidarPrecoTotal implements IValidarPedido {

    @Override
    public void validar(Pedido pedido, Integer idUsuario, List<PedidoXProduto> produtos) throws Exception {
        BigDecimal precoCarrinhoTotal = new BigDecimal(0);

        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();
            BigDecimal quantidade = new BigDecimal(pedidoXProduto.getQuantidade());

            precoCarrinhoTotal = precoCarrinhoTotal.add(produto.getPreco().multiply(quantidade));
        }
        if (pedido.getCupom() != null) {
            BigDecimal taxaDeDesconto = pedido.getCupom().getTaxaDesconto();

            BigDecimal desconto = precoCarrinhoTotal.multiply(taxaDeDesconto.divide(new BigDecimal(100)));

            precoCarrinhoTotal = precoCarrinhoTotal.subtract(desconto);

        }
        System.out.println(precoCarrinhoTotal);
        pedido.setPrecoCarrinho(precoCarrinhoTotal);

    }
}
