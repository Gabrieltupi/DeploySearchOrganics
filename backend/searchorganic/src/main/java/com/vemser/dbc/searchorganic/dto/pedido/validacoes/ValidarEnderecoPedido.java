package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidarEnderecoPedido implements IValidarPedido{
    private final EnderecoRepository enderecoRepository;
    @Override
    public void validar(PedidoCreateDTO pedidoCreateDTO, Integer idUsuario) throws Exception {
        Endereco endereco = enderecoRepository.buscarPorId(pedidoCreateDTO.getIdEndereco());
        if( !(endereco.getIdUsuario() == idUsuario.longValue())){
            throw  new ValidacaoException("O endereço enviado não pertence ao usuário que solicitou o pedido");
        }
    }
}
