package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {
    @Pattern(regexp = "^[0-9]{16}$", message = "O número do cartão deve ter 16 dígitos numéricos")
    private String numeroCartao;

    @Pattern(regexp = "^[0-9]{3}$", message = "O CVV deve ter 3 dígitos numéricos")
    private String cvv;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Data de validade inválida, verifique se a data está no futuro e no formato yyyy-MM-dd")
    private LocalDate dataValidade;

    @NotNull
    private FormaPagamento formaPagamento;
}
