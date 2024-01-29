package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.model.Pedido;

public interface IValidarPedido {
    void validar(Pedido pedido, Integer idUsuario) throws Exception;
}
