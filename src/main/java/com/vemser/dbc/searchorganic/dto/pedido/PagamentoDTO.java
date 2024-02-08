package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {
    private CartaoDTO cartao;
    @NotNull
    private FormaPagamento formaPagamento;
}
