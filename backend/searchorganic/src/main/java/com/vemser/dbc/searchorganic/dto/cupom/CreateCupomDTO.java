package com.vemser.dbc.searchorganic.dto.cupom;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCupomDTO {
    @NotNull
    private String nomeCupom;
    @NotNull
    private TipoAtivo ativo;
    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal taxaDeDesconto = new BigDecimal(0);
    @NotNull
    private Integer idEmpresa;
}
