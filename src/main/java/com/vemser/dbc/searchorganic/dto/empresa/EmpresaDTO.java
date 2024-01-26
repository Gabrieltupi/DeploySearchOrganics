package com.vemser.dbc.searchorganic.dto.empresa;

import com.vemser.dbc.searchorganic.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
    @Schema(description = "Id da empresa",required = true, example = "1")
    private Integer idEmpresa;

    @Schema(description = "Id do Usuario",required = true, example = "15")
    private Integer idUsuario;

    @NotBlank
    @Schema(description = "Nome da empresa",required = true, example = "Fazendo do Wlad")
    private String nomeFantasia;

    @CNPJ
    @Schema(description = "Cnpj da empresa",required = true, example = "50.271.776/0001-14")
    private String cnpj;

    @NotNull
    @Schema(description = "Razão Social",required = true, example = "Produzir legumes para sanar a nessecidade dos que precisam")
    private String razaoSocial;

    @NotNull
    @Schema(description = "Inscrição Social",required = true, example = "inscriçao social")
    private String inscricaoEstadual;

    @NotBlank
    @Schema(description = "setor da empresa",required = true, example = "Legumes")
    private String setor;

    @Schema(description = "Produtos",required = true, example = "Produtos")
    private ArrayList<Produto> produtos = new ArrayList<>();

}
