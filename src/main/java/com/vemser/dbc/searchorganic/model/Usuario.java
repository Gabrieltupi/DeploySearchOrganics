package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
@Entity(name = "USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NOME")
    private String nome;
    @Column(name = "SOBRENOME")
    private String sobrenome;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DATANASCIMENTO")
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "SENHA")
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(name = "ATIVO")
    private TipoAtivo tipoAtivo = TipoAtivo.S;
}