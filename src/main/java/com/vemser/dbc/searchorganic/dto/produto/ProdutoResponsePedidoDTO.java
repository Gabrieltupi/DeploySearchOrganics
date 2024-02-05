package com.vemser.dbc.searchorganic.dto.produto;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponsePedidoDTO{
    private Integer idProduto;

    @Schema(description = "id da empresa", required = true, example = "0")
    private Integer idEmpresa;

    @Schema(description = "nome do produto", required = true, example = "Maçã")
    private String nome;

    @Schema(description = "descrição produto", required = true, example = "Maça Gala")
    private String descricao;

    @Schema(description = "preço do produto", required = true, example = "3.5")
    private BigDecimal preco;

    @Schema(description = "Categoria do produto", required = true, example = "2")
    private TipoCategoria categoria;

    @Schema(description = "taxa aplicada no produto", required = true, example = "3.3")
    private double taxa;

    @Schema(description = "unidade de medida para o produto", required = true, example = "KG")
    private UnidadeMedida unidadeMedida;

    @Schema(description = "link da imagem do produto")
    private String urlImagem;

    @Schema(description = "Atividade do produto", required = true, example = "S")
    private TipoAtivo tipoAtivo;
}
