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
public class CreateCupomDTO {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(description = "Nome do cupom", required = true, example = "cupom 10%")
    private String nomeCupom;

    @NotNull
    @Schema(description = "atividade do cupom", required = true, example = "1")
    private TipoAtivo ativo;

    @NotBlank
    @NotNull
    @Schema(description = "Descrição do cupom", required = true, example = "Cupom de 10% de desconto para uso em uma nova loja")
    @Size(min = 1, max = 255)
    private String descricao;

    @NotNull
    @Schema(description = "Taxa de desconto do cupom", required = true, example = "10")
    private BigDecimal taxaDesconto = new BigDecimal(0);

}
