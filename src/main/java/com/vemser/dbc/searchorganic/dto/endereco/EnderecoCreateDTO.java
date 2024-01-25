package com.vemser.dbc.searchorganic.dto.endereco;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EnderecoCreateDTO {
    @NotNull
    private Integer idUsuario;

    @NotBlank
    @Size(min = 1, max = 255)
    private String logradouro;

    @NotBlank
    @Size(min = 1, max = 10)
    @Pattern(regexp = "^[0-9]+$", message = "deve conter apenas números")
    private String numero;

    @NotBlank
    @Size(max = 20)
    private String complemento;

    @NotBlank
    @Size(min = 9, max = 9)
    private String cep;

    @NotBlank
    @Size(min = 1, max = 20)
    private String cidade;

    @NotBlank
    @Size(min = 1, max = 20)
    private String estado;

    @NotBlank
    @Size(min = 1, max = 50)
    private String pais;
}
