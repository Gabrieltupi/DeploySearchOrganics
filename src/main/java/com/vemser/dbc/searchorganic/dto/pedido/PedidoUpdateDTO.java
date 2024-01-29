package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.validadores.TipoEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoUpdateDTO {
    @NotNull
    private Integer idEndereco;

    @NotNull
    @Schema(description = "Forma de pagamento", required = true, example = "PIX")
    private FormaPagamento formaPagamento;

    @NotNull
    @Schema(description = "Status do pedido", required = true, example = "AGUARDANDO_PAGAMENTO")
    private StatusPedido statusPedido;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data que o pedido será entregue", required = true, example = "aaaa/mm/dd")
    private LocalDate dataEntrega;

    @NotNull
    @Schema(description = "Tipo da entrega ao cliente", required = true, example = "RETIRAR_NO_LOCAL")
    private TipoEntrega tipoEntrega;

    @NotNull
    @Schema(description = "Preço do frete", required = true, example = "9")
    private BigDecimal precoFrete;
}
