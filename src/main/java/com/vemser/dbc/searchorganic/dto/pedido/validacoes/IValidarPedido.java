package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;

import java.util.List;

public interface IValidarPedido {
    void validar(Pedido pedido, Integer idUsuario, List<PedidoXProduto> produtos) throws Exception;
}
