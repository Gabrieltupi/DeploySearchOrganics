package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.dto.produto.ProdutoResponsePedidoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoPedidoDTO {
    private ProdutoResponsePedidoDTO produto;
    private Integer quantidade;
}
