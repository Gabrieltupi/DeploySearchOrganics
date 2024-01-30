package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(hidden = true)
@Entity(name = "PEDIDOXPRODUTO")
public class ProdutoCarrinho implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO")
    private Pedido pedido;

    @Id
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "ID_PRODUTO")
    private Produto produto;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;

}


