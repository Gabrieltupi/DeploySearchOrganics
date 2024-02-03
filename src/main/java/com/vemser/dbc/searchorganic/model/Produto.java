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
    @Column(name="ID_PRODUTO")
    private Integer idProduto;

    @Column(name="ID_EMPRESA")
    private Integer idEmpresa;

    @Column(name="NOME")
    private String nome;

    @Column(name="DESCRICAO")
    private String descricao;

    @Column(name="PRECO")
    private BigDecimal preco;

    @Column(name="QUANTIDADE")
    private BigDecimal quantidade;

    @Column(name="TAXA")
    private double taxa;

    @Column(name = "URL_IMAGEM")
    private String urlImagem;

    @OneToMany(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PedidoXProduto> pedidoXProduto;

    @Column(name = "TIPO_CATEGORIA")
    @Enumerated(EnumType.ORDINAL)
    private TipoCategoria categoria;

    @Column(name = "UNIDADE_MEDIDA")
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;

    @Column(name = "ATIVO")
    @Enumerated(EnumType.STRING)
    private TipoAtivo tipoAtivo;
}