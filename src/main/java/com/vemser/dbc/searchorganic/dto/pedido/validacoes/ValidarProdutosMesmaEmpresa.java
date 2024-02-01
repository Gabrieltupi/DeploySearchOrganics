package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidarProdutosMesmaEmpresa implements IValidarPedido {

    @Override
    public void validar(Pedido pedido, Integer idUsuario, List<PedidoXProduto> produtos) throws Exception {
        Integer idEmpresa = produtos.get(0).getProduto().getEmpresa().getIdEmpresa();
        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();
            if (!(produto.getEmpresa().getIdEmpresa() == idEmpresa.longValue())) {
                throw new ValidacaoException("Produtos não pertencem a mesma empresa");
            }

        }
    }
}
