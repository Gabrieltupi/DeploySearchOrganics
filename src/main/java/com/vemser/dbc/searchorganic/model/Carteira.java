package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "CARTEIRA")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARTEIRA_SEQ")
    @SequenceGenerator(name = "CARTEIRA_SEQ", sequenceName = "seq_carteira", allocationSize = 1)
    @Column(name = "id_carteira")
    private Integer idCarteira;

    @Column(name = "saldo")
    private BigDecimal saldo;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Carteira(Usuario usuario) {
        this.saldo = new BigDecimal(0);
        this.usuario = usuario;
    }
}
