package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
@Entity(name = "USUARIO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NOME")
    private String nome;
    @Column(name = "SOBRENOME")
    private String sobrenome;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DATANASCIMENTO")
    private LocalDate dataNascimento;
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