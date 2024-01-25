package com.vemser.dbc.searchorganic.dto.endereco;


import lombok.Data;

@Data
public class EnderecoDTO {
    private Integer idEndereco;
    private Integer idUsuario;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
    private String regiao;
}
