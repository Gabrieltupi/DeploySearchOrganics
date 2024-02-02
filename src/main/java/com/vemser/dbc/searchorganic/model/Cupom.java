package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "CUPOM")
@NoArgsConstructor
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUPOM_SEQ")
    @SequenceGenerator(name = "CUPOM_SEQ", sequenceName = "seq_cupom", allocationSize = 1)
    @Column(name = "id_cupom")
    private Integer idCupom;

    @Column(name = "nome_cupom")
    private String nomeCupom;

    @Column(name = "ativo")
    @Enumerated(EnumType.STRING)
    private TipoAtivo ativo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "taxa_desconto")
    private BigDecimal taxaDesconto;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_empresa")
    @Column(name = "id_empresa")
    private Integer idEmpresa;
}

