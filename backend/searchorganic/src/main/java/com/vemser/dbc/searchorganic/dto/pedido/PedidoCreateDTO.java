package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreateDTO {
    @NotBlank
    @NotNull
    private Integer idUsuario;
    @NotBlank
    @NotNull
    private Integer idEndereco;
    @NotBlank
    @NotNull
    private Integer idCupom;
    @NotBlank
    @NotNull
    private BigDecimal total;
    @NotBlank
    @NotNull
    private FormaPagamento formaPagamento;
    @NotBlank
    @NotNull
    private LocalDate dataDeEntrega;
    @NotBlank
    @NotNull
    private BigDecimal precoFrete;
    @NotBlank
    @NotNull
    private BigDecimal precoCarrinho;
    @NotBlank
    @NotNull
    private ArrayList<ProdutoCarrinho> produtos;
}
