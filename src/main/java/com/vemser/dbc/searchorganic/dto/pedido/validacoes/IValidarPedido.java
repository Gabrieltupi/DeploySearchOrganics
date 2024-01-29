package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;

public interface IValidarPedido {
    void validar(PedidoCreateDTO pedidoCreateDTO, Integer idUsuario) throws Exception;
}
