package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
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
    private String urlImagem;
    private TipoAtivo tipoAtivo;
}



