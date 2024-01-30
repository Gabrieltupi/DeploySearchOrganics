package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(hidden = true)
@Entity(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
    @SequenceGenerator(name = "PEDIDO_SEQ", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
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
    @Enumerated(EnumType.STRING)
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
