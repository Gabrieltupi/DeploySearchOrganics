package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEmpresaDTO {
    private Integer idEmpresa;
    private Integer idPedido;
    private StatusPedido statusPedido;

}