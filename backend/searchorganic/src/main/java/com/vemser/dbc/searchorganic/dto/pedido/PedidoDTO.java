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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer Id;
    private Integer UsuarioId;
    private BigDecimal total;
    private FormaPagamento formaPagamento;
    private StatusPedido statusPedido;
    private Boolean entregue;
    private LocalDate dataDeEntrega;
    private Endereco endereco;
    private LocalDate inicioEntrega;
    private TipoEntrega tipoEntrega;
    private ArrayList<ProdutoCarrinho> produtos;
    private Cupom cupom;
    private BigDecimal valorFrete = new BigDecimal(0);
    private BigDecimal precoCarrinho = new BigDecimal(0);
}
