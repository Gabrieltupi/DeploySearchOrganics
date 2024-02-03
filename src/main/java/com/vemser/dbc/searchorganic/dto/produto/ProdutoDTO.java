package com.vemser.dbc.searchorganic.dto.produto;

import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO extends ProdutoCreateDTO {
    private Integer idProduto;

    @NotNull(message = "Produto deve pertencer a um fornecedor")
    @Schema(description = "id da empresa", required = true, example = "0")
    private Integer idEmpresa;

    @NotBlank(message = "O nome nao deve ser vazio")
    @Schema(description = "nome do produto", required = true, example = "Maçã")
    private String nome;

    @NotNull(message = "Digite algo sobre o produto")
    @Schema(description = "descrição produto", required = true, example = "Maça Gala")
    private String descricao;

    @NotNull(message = "O preço não pode ser vazio")
    @Schema(description = "preço do produto", required = true, example = "3.5")
    private BigDecimal preco;

    @NotNull(message = "A quantidade deve ser informada")
    @Schema(description = "quantidade disponivel do produto", required = true, example = "500")
    private BigDecimal quantidade;

    @NotNull(message = "Informe a categoria do produto")
    @Schema(description = "Categoria do produto", required = true, example = "2")
    private TipoCategoria categoria;

    @Schema(description = "taxa aplicada no produto", required = true, example = "3.3")
    private double taxa;

    @NotNull(message = "Aquantidade deve ter uma unidade de medida")
    @Schema(description = "unidade de medida para o produto", required = true, example = "KG")
    private UnidadeMedida unidadeMedida;

    @Schema(description = "link da imagem do produto")
    private String urlImagem;

    @Schema(description = "Atividade do produto", required = true, example = "S")
    private TipoAtivo tipoAtivo;
}
