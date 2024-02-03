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
    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nomefantasia")
    private String nomeFantasia;

    @Column(name = "razaosocial")
    private String razaoSocial;

    @Column(name = "inscricaoestadual")
    private String inscricaoEstadual;

    @Column(name = "setor")
    private String setor;

    @Column(name = "cnpj")
    private String cnpj;

    @OneToMany(mappedBy = "idEmpresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Produto> produtos;
}
