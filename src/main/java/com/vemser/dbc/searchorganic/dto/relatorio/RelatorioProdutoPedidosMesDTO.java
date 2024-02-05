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
public class RelatorioProdutoPedidosMesDTO {
    @Schema(description = "Nome do produto")
    private String nome;

    @Schema(description = "Mes de referencia")
    private String mes;

    @Schema(description = "Pedidos do produto no mês")
    private Long pedidos;
}
