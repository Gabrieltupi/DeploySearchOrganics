package com.vemser.dbc.searchorganic.dto.cupom;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCupomDTO {
    @NotNull
    private Integer idEmpresa;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(description = "Nome do cupom", required = true, example = "cupom 10%")
    private String nomeCupom;

    @NotNull
    @NotBlank
    @Schema(description = "atividade do cupom", required = true, example = "cupom inativo")
    private TipoAtivo ativo;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Schema(description = "Descrição do cupom", required = true, example = "cupom de 10% de desconto para uso em uma nova loja")
    private String descricao;

    @NotNull
    @Schema(description = "Taxa de desconto do cupom", required = true, example = "10")
    private BigDecimal taxaDesconto = new BigDecimal(0);
}
