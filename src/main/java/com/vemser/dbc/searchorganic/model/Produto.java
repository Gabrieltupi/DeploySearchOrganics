package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
@Entity
        @Table(name = "PRODUTO")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_SEQ")
    @SequenceGenerator(name = "PRODUTO_SEQ", sequenceName = "seq_produto",allocationSize = 1)
    @Column(name="id_produto")
    private Integer idProduto;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="id_empresa")
    private Empresa empresa;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Column
    private BigDecimal preco;

    @Column(name = "quantidade")
    private BigDecimal quantidade;

    @Column(name= "tipo_categoria")
    private TipoCategoria categoria;

    @Column
    private double taxa;

    @Column(name="unidade_medida")
    private UnidadeMedida unidadeMedida;

    @Column(name = "url_imagem")
    private String urlImagem;

    @Column(name = "ativo")
    private TipoAtivo tipoAtivo;

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PedidoXProduto> pedidoXProduto;

}



