package com.vemser.dbc.searchorganic.dto.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
    @Schema(description = "Id da empresa", required = true)
    private Integer idEmpresa;

    @Schema(description = "Id do usuário dono da empresa", required = true)
    private Integer idUsuario;

    @Schema(description = "Nome da empresa", required = true, example = "Fazendo do Wlad")
    private String nomeFantasia;

    @Schema(description = "Cnpj da empresa", required = true, example = "5027177600114")
    private String cnpj;

    @Schema(description = "Razão Social", required = true, example = "Produzir legumes para sanar a nessecidade dos que precisam")
    private String razaoSocial;

    @Schema(description = "Inscrição Social", required = true, example = "654987321")
    private String inscricaoEstadual;

    @Schema(description = "setor da empresa", required = true, example = "Legumes")
    private String setor;
}