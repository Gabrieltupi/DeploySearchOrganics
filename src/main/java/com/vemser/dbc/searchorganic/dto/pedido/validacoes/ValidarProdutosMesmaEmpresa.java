package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import com.vemser.dbc.searchorganic.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidarProdutosMesmaEmpresa implements IValidarPedido {
    @Override
    public void validar(Pedido pedido, Integer idUsuario, List<PedidoXProduto> produtos) throws Exception {
        Integer idEmpresa = pedido.getEmpresa().getIdEmpresa();
        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();
            if (produto.getIdEmpresa() != idEmpresa) {
                throw new ValidacaoException("Produto: " + produto.getNome() + " não pertence a empresa.");
            }

        }
    }
}
