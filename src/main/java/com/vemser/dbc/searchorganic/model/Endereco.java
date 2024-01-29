package com.vemser.dbc.searchorganic.model;


import com.vemser.dbc.searchorganic.interfaces.IImpressao;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class Endereco implements IImpressao {
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
    private ValidadorCEP validadorCEP;

    public Endereco(String logradouro, String numero, String complemento, String cep, String cidade,
                    String estado, String pais) {
        if(ValidadorCEP.isCepValido(cep) != null) {
            this.logradouro = logradouro;
            this.numero = numero;
            this.complemento = complemento;
            this.cep = cep;
            this.cidade = cidade;
            this.estado = estado;
            this.pais = pais;
            this.regiao = ValidadorCEP.isCepValido(cep);
        }
    }

    public Endereco(String logradouro, String numero, String complemento, String cep, String cidade,
                    String estado, String pais, int idUsuario) {
        if(ValidadorCEP.isCepValido(cep) != null) {
            this.idUsuario = idUsuario;
            this.logradouro = logradouro;
            this.numero = numero;
            this.complemento = complemento;
            this.cep = cep;
            this.cidade = cidade;
            this.estado = estado;
            this.pais = pais;
            this.regiao = ValidadorCEP.isCepValido(cep);
        } else{
            System.out.println("Ainda não atendemos neste estado");
        }
    }

    @Override
    public void imprimir() {
        System.out.printf("Logradouro: %s ", this.getLogradouro());
        System.out.printf("Número: %s ", this.getNumero());
        System.out.printf("Complemento: %s\n", this.getComplemento());
        System.out.printf("CEP: %s\n", this.getCep());
        System.out.printf("Regiao: %s ", this.getRegiao());
        System.out.printf("Cidade: %s ", this.getCidade());
        System.out.printf("Estado: %s\n", this.getEstado());
        System.out.printf("País: %s\n", this.getPais());
    }
}
