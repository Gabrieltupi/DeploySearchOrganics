package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PRODUTO")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_SEQ")
    @SequenceGenerator(name = "PRODUTO_SEQ", sequenceName = "seq_produto",allocationSize = 1)
    @Column(name="id_produto")
    private Integer idProduto;

    @Column(name="id_empresa")
    private Integer idEmpresa;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column
    private BigDecimal preco;

    @Column(name = "quantidade")
    private BigDecimal quantidade;

    @Column
    private double taxa;

    @Column(name = "url_imagem")
    private String urlImagem;

    @OneToMany(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PedidoXProduto> pedidoXProduto;

    @Column(name = "tipo_categoria")
    @Enumerated(EnumType.ORDINAL)
    private TipoCategoria categoria;

    @Column(name = "unidade_medida")
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;

    @Column(name = "ativo")
    @Enumerated(EnumType.STRING)
    private TipoAtivo tipoAtivo;
}