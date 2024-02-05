package com.vemser.dbc.searchorganic.dto.relatorio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioProdutoPedidosDTO {
    @Schema(description = "Nome do produto")
    private String nome;

    @Schema(description = "Pedidos do produto")
    private Long pedidos;
}
