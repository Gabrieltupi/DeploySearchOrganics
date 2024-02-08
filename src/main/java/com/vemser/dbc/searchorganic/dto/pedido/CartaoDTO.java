package com.vemser.dbc.searchorganic.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO {

    @Pattern(regexp = "^[0-9]{16}$", message = "O número do cartão deve ter 16 dígitos numéricos")
    private String numeroCartao;

    @Pattern(regexp = "^[0-9]{3}$", message = "O CVV deve ter 3 dígitos numéricos")
    private String cvv;

    @Future
    @DateTimeFormat(pattern = "MM/yyyy")
    private String dataValidade;
}
