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
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer idPedido;
    private Integer idUsuario;
    private StatusPedido statusPedido;
    private BigDecimal precoFrete;
    private BigDecimal precoCarrinho;
    private BigDecimal total;
    private FormaPagamento formaPagamento;
    private LocalDate dataDePedido;
    private LocalDate dataEntrega;
    private Endereco endereco;
    private ArrayList<ProdutoCarrinho> produtos;
    private Cupom cupom;
}
