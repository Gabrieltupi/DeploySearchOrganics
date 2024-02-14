package com.vemser.dbc.searchorganic.model.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
