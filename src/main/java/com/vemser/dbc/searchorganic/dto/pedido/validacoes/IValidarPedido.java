package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;

public interface IValidarPedido {
    void validar(PedidoCreateDTO pedidoCreateDTO, Integer idUsuario) throws Exception;
}
