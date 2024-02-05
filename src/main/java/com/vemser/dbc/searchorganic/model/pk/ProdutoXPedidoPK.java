package com.vemser.dbc.searchorganic.model.pk;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoXPedidoPK implements Serializable {

    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "ID_PEDIDO")
    private Integer idPedido;

}
