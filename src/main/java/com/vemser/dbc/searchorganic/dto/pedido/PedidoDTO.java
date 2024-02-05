package com.vemser.dbc.searchorganic.dto.pedido;

import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
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
    private List<ProdutoPedidoDTO> produtos;
    private CupomDTO cupom;

    public PedidoDTO(Pedido pedido, UsuarioDTO usuarioDTO, EnderecoDTO enderecoDTO, CupomDTO cupomDto, List<ProdutoPedidoDTO> produtos) {
        this.idPedido = pedido.getIdPedido();
        this.usuario = usuarioDTO;
        this.statusPedido = pedido.getStatusPedido();
        this.precoFrete = pedido.getPrecoFrete();
        this.precoCarrinho = pedido.getPrecoCarrinho();
        this.total = pedido.getPrecoCarrinho().add(pedido.getPrecoFrete());
        this.formaPagamento = pedido.getFormaPagamento();
        this.dataDePedido = pedido.getDataDePedido();
        this.dataEntrega = pedido.getDataEntrega();
        this.endereco = enderecoDTO;
         this.produtos = produtos;
         this.cupom = cupomDto;
    }
}
