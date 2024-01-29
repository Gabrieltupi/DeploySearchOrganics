package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(hidden = true)
public class Cupom {

    private Integer cupomId;
    @NotEmpty(message = "O nome nao pode ser vazio")
    @Size(min = 2, max = 30, message = "O nome conter entre 2 a 30 caracteres!")
    private String nomeCupom;

    @NotNull
    private TipoAtivo ativo;
    @NotEmpty(message = "A descricao nao pode ser vazia")
    @Size(min = 2, max = 100, message = "A descricao conter entre 2 a 100 caracteres!")

    private String descricao;

    @NotEmpty(message = "A taxa de desconto nao pode ser vazia")
    private BigDecimal taxaDeDesconto;
    private Integer idEmpresa;


}

