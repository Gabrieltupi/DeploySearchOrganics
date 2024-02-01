package com.vemser.dbc.searchorganic.dto.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnderecoCreateDTO {


    @NotNull
    private Integer idUsuario;

    @NotBlank
    @Size(min = 1, max = 255)
    @Schema(description = "Logradouro do usuario", required = true, example = "AV.Juventino Machado")
    private String logradouro;

    @NotBlank
    @Size(min = 1, max = 10)
    @Pattern(regexp = "^[0-9]+$", message = "deve conter apenas números")
    @Schema(description = "Numero da residência", required = true, example = "665")
    private String numero;

    @NotBlank
    @Size(max = 20)
    @Schema(description = "Complemento do endereço", required = true, example = "Perto do SuperMercado Menger")
    private String complemento;

    @NotBlank
    @Size(min = 9, max = 9)
    @Schema(description = "cnpj", required = true, example = "93230600")
    private String cep;

    @NotBlank
    @Size(min = 1, max = 20)
    @Schema(description = "Cidade", required = true, example = "Sapucaia do Sul")
    private String cidade;

    @NotBlank
    @Size(min = 1, max = 20)
    @Schema(description = "Estado", required = true, example = "Rio Grande do Sul")
    private String estado;

    @NotBlank
    @Size(min = 1, max = 20)
    @Schema(description = "Regiao", required = true, example = "Coloque regiao")
    private String regiao;

    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(description = "Pais", required = true, example = "Brasil")
    private String pais;
}
