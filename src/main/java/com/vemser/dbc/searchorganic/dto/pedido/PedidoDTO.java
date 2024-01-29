package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.dto.cupom.CupomDto;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer idPedido;
    private UsuarioDTO usuario;
    private StatusPedido statusPedido;
    private BigDecimal precoFrete;
    private BigDecimal precoCarrinho;
    private BigDecimal total;
    private FormaPagamento formaPagamento;
    private LocalDate dataDePedido;
    private LocalDate dataEntrega;
    private EnderecoDTO endereco;
    private List<ProdutoCarrinho> produtos;
    private CupomDto cupom;
}
