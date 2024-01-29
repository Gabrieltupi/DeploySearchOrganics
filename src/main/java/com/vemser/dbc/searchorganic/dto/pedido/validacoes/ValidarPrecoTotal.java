package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.repository.CupomRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ValidarPrecoTotal implements IValidarPedido {
    private final ProdutoRepository produtoRepository;
    private final CupomRepository cupomRepository;

    @Override
    public void validar(PedidoCreateDTO pedidoCreateDTO, Integer idUsuario) throws Exception {
        BigDecimal precoCarrinhoTotal = new BigDecimal(0);
        ;
        for (ProdutoCarrinho produtoCarrinho : pedidoCreateDTO.getProdutos()) {
            Produto produto = produtoRepository.buscarProdutoPorId(produtoCarrinho.getIdProduto());
            precoCarrinhoTotal.add(produto.getPreco());
        }
        if (pedidoCreateDTO.getIdCupom() != null) {
            Cupom cupom = cupomRepository.buscarPorId(pedidoCreateDTO.getIdCupom());
            if (cupom != null) {
                BigDecimal taxaDeDesconto = cupom.getTaxaDeDesconto();

                BigDecimal desconto = precoCarrinhoTotal.multiply(taxaDeDesconto.divide(new BigDecimal(100)));

                precoCarrinhoTotal = precoCarrinhoTotal.subtract(desconto);
            }
        }


        pedidoCreateDTO.setPrecoCarrinho(precoCarrinhoTotal);

    }
}
