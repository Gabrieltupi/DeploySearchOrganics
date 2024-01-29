package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.validadores.TipoEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(hidden = true)
@Entity(name = "PEDIDO")
public class Pedido {
    @Id
    @Column(name = "ID_PEDIDO")
    private Integer idPedido;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @ManyToOne
     @JoinColumn(name = "ID_CUPOM", nullable = true)
    private Cupom cupom;

    @Column(name = "FORMA_PAGAMENTO")
    private FormaPagamento formaPagamento;

    @Column(name = "STATUS_PEDIDO")
    private StatusPedido statusPedido;

    @Column(name = "DATA_DE_PEDIDO")
    private LocalDate dataDePedido;

    @Column(name = "DATA_ENTREGA")
    private LocalDate dataEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ProdutoCarrinho> produtos;

    @Column(name = "PRECO_FRETE")
    private BigDecimal precoFrete;

    @Column(name = "PRECO_CARRINHO")
    private BigDecimal precoCarrinho;

}
