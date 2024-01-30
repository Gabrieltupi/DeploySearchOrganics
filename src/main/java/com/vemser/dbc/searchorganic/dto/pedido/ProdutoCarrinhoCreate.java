package com.vemser.dbc.searchorganic.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCarrinhoCreate {
    @NotNull
    private Integer idProduto;

    @NotNull
    private Integer quantidade;

}


