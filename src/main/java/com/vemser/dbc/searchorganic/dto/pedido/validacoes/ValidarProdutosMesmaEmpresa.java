package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidarProdutosMesmaEmpresa implements IValidarPedido {

    @Override
    public void validar(Pedido pedido, Integer idUsuario) throws Exception {
        Integer idEmpresa = pedido.getProdutos().get(0).getProduto().getIdEmpresa();
        for (ProdutoCarrinho produtoCarrinho : pedido.getProdutos()) {
            Produto produto = produtoCarrinho.getProduto();
            if (!(produto.getIdEmpresa() == idEmpresa.longValue())) {
                throw new ValidacaoException("Produtos não pertencem a mesma empresa");
            }

        }
    }
}
