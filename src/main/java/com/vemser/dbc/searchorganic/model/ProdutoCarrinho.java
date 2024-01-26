package com.vemser.dbc.searchorganic.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdutoCarrinho{
    private Integer idProduto;
    private Integer quantidade;
    private Integer idEmpresa;
    private Produto produto;

    public ProdutoCarrinho(Integer idProduto, Integer quantidade, Integer idEmpresa) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.idEmpresa = idEmpresa;
    }

    public ProdutoCarrinho(Integer idProduto, Integer quantidade, Integer idEmpresa, Produto produto) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.idEmpresa = idEmpresa;
        this.produto = produto;
    }
}


