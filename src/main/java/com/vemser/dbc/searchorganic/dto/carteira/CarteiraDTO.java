package com.vemser.dbc.searchorganic.dto.carteira;

import com.vemser.dbc.searchorganic.model.Carteira;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class CarteiraDTO {
    @Schema(description = "ID da carteira", example = "1")
    private Integer idCarteira;

    @Schema(description = "Saldo da carteira", example = "100")
    private BigDecimal saldo;

    public CarteiraDTO(Carteira carteira) {
        this.idCarteira = carteira.getIdCarteira();
        this.saldo = carteira.getSaldo();
    }
}
