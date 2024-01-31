package com.vemser.dbc.searchorganic.dto.cupom;

import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Nome do cupom", required = true, example = "cupom 10%")
    private String nomeCupom;

    @Schema(description = "atividade do cupom", required = true, example = "cupom inativo")
    private TipoAtivo ativo;

    @Size(min = 1, max = 255)
    @Schema(description = "Descrição do cupom", required = true, example = "cupom de 10% de desconto para uso em uma nova loja")
    private String descricao;

    @Schema(description = "Taxa de desconto do cupom", required = true, example = "10")
    private BigDecimal taxaDeDesconto = new BigDecimal(0);

    @NotBlank
    @Schema(description = "Id daa empresa a ser aplicado o cupom", required = true, example = "Fazendo do Wlad")
    private Integer idEmpresa;

    public CupomDto(Cupom cupom) {
        this.nomeCupom = cupom.getNomeCupom();
        this.ativo = cupom.getAtivo();
        this.descricao = cupom.getDescricao();
        this.taxaDeDesconto = cupom.getTaxaDeDesconto();
        this.idEmpresa = cupom.getIdEmpresa();
    }
}
