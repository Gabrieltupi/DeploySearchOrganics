package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {
    @NotNull
    private Integer idEndereco;

    @Schema(description = "id cupom", required = true, example = "id cupom")
    private Integer idCupom;

    @NotNull
    @Schema(description = "Forma de pagamento", required = true, example = "PIX")
    private FormaPagamento formaPagamento;

    @Schema(description = "Data que o pedido foi feito", required = true, example = "aaaa/mm/dd")
    private LocalDate dataDePedido = LocalDate.now();

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data que o pedido será entregue", required = true, example = "aaaa/mm/dd")
    private LocalDate dataEntrega;

    @NotNull
    @Schema(description = "Preço do frete", required = true, example = "9")
    private BigDecimal precoFrete;

    @NotNull
    @Schema(description = "Preço do carrinho", required = true, example = "100")
    private BigDecimal precoCarrinho;

    @NotNull
    @Schema(description = "produtos carrinho", required = true, example = "[]")
    private ArrayList<ProdutoCarrinhoCreate> produtosCarrinho;

}
