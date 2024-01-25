package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {
    @NotNull
    @Size
    private Integer idEndereco;
    @NotNull
    private Integer idCupom;
    @NotNull
    private FormaPagamento formaPagamento;
    private LocalDate dataDePedido = LocalDate.now();
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrega;
    @NotNull
    private BigDecimal precoFrete;
    @NotNull
    private BigDecimal precoCarrinho;
    @NotNull
    private ArrayList<ProdutoCarrinho> produtos;

}
