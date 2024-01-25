package com.vemser.dbc.searchorganic.dto.cupom;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
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
    private String nomeCupom;
    @NotNull
    @NotBlank
    private TipoAtivo ativo;
    @NotBlank
    @NotNull
    @Size(min = 1, max = 255)
    private String descricao;
    @NotBlank
    @NotNull
    private BigDecimal taxaDeDesconto = new BigDecimal(0);
    @NotBlank
    @NotNull
    @Size(min = 1,max = 38)
    private Integer idEmpresa;
}
