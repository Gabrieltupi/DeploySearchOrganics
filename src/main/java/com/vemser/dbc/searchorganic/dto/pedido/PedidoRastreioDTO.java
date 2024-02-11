package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoRastreioDTO {
    private Integer idPedido;
    private String codigoDeRastreio;
    private StatusPedido statusPedido;
}
