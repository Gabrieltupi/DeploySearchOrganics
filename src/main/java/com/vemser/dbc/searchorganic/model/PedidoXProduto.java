package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vemser.dbc.searchorganic.model.pk.ProdutoXPedidoPK;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(hidden = true)
@Entity(name = "PEDIDOXPRODUTO")
public class PedidoXProduto {
    @JsonIgnore
    @EmbeddedId
    private ProdutoXPedidoPK produtoXPedidoPK;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", insertable = false, updatable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO", insertable = false, updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private Pedido pedido;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;
}