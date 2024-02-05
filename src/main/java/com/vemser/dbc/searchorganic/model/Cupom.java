package com.vemser.dbc.searchorganic.model;

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
    @Column(name = "ID_CUPOM")
    private Integer idCupom;

    @Column(name = "NOME_CUPOM")
    private String nomeCupom;

    @Column(name = "ATIVO")
    @Enumerated(EnumType.STRING)
    private TipoAtivo ativo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "TAXA_DESCONTO")
    private BigDecimal taxaDesconto;

    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;
}

