package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CARGO")
public class Cargo implements GrantedAuthority {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_CARGO")
    private Integer idCargo;

    @Column(name = "NOME")
    private String nome;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "USUARIO_CARGO",
            joinColumns = @JoinColumn(name = "ID_CARGO"),
            inverseJoinColumns = @JoinColumn(name = "ID_USUARIO")
    )
    private Set<Usuario> usuarios;

    @Override
    public String getAuthority() {
        return nome;
    }
}