package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.validadores.TipoEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(hidden = true)
public class Pedido {
    private Integer idPedido;
    private Integer idUsuario;
    private Integer idEndereco;
    private Integer idCupom;
    private FormaPagamento formaPagamento;
    private StatusPedido statusPedido;
    private LocalDate dataDePedido;
    private LocalDate dataEntrega;
    private TipoEntrega tipoEntrega;
    private ArrayList<ProdutoCarrinho> produtos;
    private BigDecimal precoFrete;
    private BigDecimal precoCarrinho;

}
