package com.vemser.dbc.searchorganic.dto.cupom;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CupomDto {
    private String nomeCupom;
    private TipoAtivo ativo;
    private String descricao;
    private BigDecimal taxaDeDesconto = new BigDecimal(0);
    private Integer idEmpresa;
}
