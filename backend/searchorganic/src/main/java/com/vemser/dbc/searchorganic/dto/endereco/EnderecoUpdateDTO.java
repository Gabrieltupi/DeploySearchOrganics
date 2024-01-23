package com.vemser.dbc.searchorganic.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoUpdateDTO {
    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "deve conter apenas números")
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
    @Size(min = 8, max = 8)
    @Pattern(regexp = "^[0-9]+$", message = "deve conter apenas números")
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

    @NotBlank
    @Size(min = 1, max = 50)
    private String regiao;
}
