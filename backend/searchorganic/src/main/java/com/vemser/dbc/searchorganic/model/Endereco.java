package com.vemser.dbc.searchorganic.model;


import com.vemser.dbc.searchorganic.interfaces.IImpressao;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class Endereco implements IImpressao {
    private Integer id;
    private Integer idUsuario;
    @NotEmpty(message = "logradouro nao pode ser nulo ou vazio")
    private String logradouro;
    @NotEmpty(message = "numero nao pode ser nulo ou vazio")
    private String numero;
    @NotEmpty(message = "complemento nao pode ser nulo ou vazio")
    private String complemento;
    @NotEmpty(message = "cep nao pode ser nulo ou vazio")
    private String cep;
    @NotEmpty(message = "cidade nao pode ser nulo ou vazio")
    private String cidade;
    @NotEmpty(message = "estado nao pode ser nulo ou vazio")
    private String estado;
    @NotEmpty(message = "pais nao pode ser nulo ou vazio")
    private String pais;
    @NotEmpty(message = "regiao nao pode ser nulo ou vazio")
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

    public void setCep(String cep) {
        this.cep = cep;
        this.regiao = ValidadorCEP.isCepValido(cep);
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
