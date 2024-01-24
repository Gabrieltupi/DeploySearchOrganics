package com.vemser.dbc.searchorganic.dto.cupom;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CupomDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String nomeCupom;
    @NotBlank
    private TipoAtivo ativo;
    @NotBlank
    @Size(min = 1, max = 255)
    private String descricao;
    @NotBlank
    private BigDecimal taxaDeDesconto = new BigDecimal(0);
    @NotBlank
    private Integer idEmpresa;
}
