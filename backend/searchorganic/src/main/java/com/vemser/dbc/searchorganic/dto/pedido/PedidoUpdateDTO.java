package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.validadores.TipoEntrega;
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
public class PedidoUpdateDTO {
    @NotBlank
    @NotNull
    private Integer idEndereco;
    @NotBlank
    @NotNull
    private Integer idCupom;
    @NotBlank
    @NotNull
    private FormaPagamento formaPagamento;
    @NotBlank
    @NotNull
    private StatusPedido statusPedido;
    @NotBlank
    @NotNull
    private Boolean entregue;
    @NotBlank
    @NotNull
    private LocalDate dataDeEntrega;
    @NotBlank
    @NotNull
    private LocalDate inicioEntrega;
    @NotBlank
    @NotNull
    private TipoEntrega tipoEntrega;
    @NotBlank
    @NotNull
    private ArrayList<ProdutoCarrinho> produtos;
}
