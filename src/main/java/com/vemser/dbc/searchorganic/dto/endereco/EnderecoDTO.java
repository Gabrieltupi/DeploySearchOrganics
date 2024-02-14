package com.vemser.dbc.searchorganic.dto.endereco;


import com.vemser.dbc.searchorganic.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private Integer idEndereco;
    private Integer idUsuario;

    @Schema(description = "Logradouro do usuario", required = true, example = "AV.Juventino Machado")
    private String logradouro;

    @Schema(description = "Numero da residência", required = true, example = "665")
    private String numero;

    @Schema(description = "Complemento do endereço", required = true, example = "Perto do SuperMercado Menger")
    private String complemento;

    @Schema(description = "cnpj", required = true, example = "93230600")
    private String cep;

    @Schema(description = "Cidade", required = true, example = "Sapucaia do Sul")
    private String cidade;

    @Schema(description = "Estado", required = true, example = "Rio Grande do Sul")
    private String estado;

    @Schema(description = "Pais", required = true, example = "Brasil")
    private String pais;
    private String regiao;

    public EnderecoDTO(Endereco endereco) {
        this.idEndereco = endereco.getIdEndereco();
        this.idUsuario = endereco.getUsuario().getIdUsuario();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.cep = endereco.getCep();
        this.cidade = endereco.getCidade();
        this.estado = endereco.getEstado();
        this.pais = endereco.getPais();
        this.regiao = endereco.getRegiao();
    }
}
