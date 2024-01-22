package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto   {
    private Integer idProduto;
    private Integer idEmpresa;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private BigDecimal quantidade;
    private TipoCategoria categoria;
    private double taxa;
    private UnidadeMedida unidadeMedida;

}




//
//    public Produto(int id_Produto, String nome, String descricao, BigDecimal preco,
//                   BigDecimal quantidade, TipoCategoria categoria, double taxa,
//                   UnidadeMedida unidadeMedida, Empresa empresa) {
//        this.id_Produto = id_Produto;
//        this.id_empresa = empresa.getIdEmpresa();
//        this.empresa = empresa;
//        this.nome = nome;
//        this.descricao = descricao;
//        this.preco = preco;
//        this.quantidade = quantidade;
//        this.categoria = categoria;
//        this.taxa = taxa;
//        this.unidadeMedida = unidadeMedida;
//        gerarProximoId();
//    }
