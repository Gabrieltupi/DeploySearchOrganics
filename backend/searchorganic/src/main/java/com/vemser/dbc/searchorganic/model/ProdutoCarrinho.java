package com.vemser.dbc.searchorganic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCarrinho{
    private Integer idProduto;
    private Integer quantidade;
    private Integer idEmpresa;
}
