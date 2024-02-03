package com.vemser.dbc.searchorganic.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "EMPRESA")
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPRESA_SEQ")
    @SequenceGenerator(name = "EMPRESA_SEQ", sequenceName = "seq_empresa", allocationSize = 1)
    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;

    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NOMEFANTASIA")
    private String nomeFantasia;

    @Column(name = "RAZAOSOCIAL")
    private String razaoSocial;

    @Column(name = "INSCRICAOESTADUAL")
    private String inscricaoEstadual;

    @Column(name = "SETOR")
    private String setor;

    @Column(name = "CNPJ")
    private String cnpj;

    @OneToMany(mappedBy = "idEmpresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Produto> produtos;
}
