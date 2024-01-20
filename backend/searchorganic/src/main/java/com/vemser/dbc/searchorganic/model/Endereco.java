package com.vemser.dbc.searchorganic.model;


import com.vemser.dbc.searchorganic.interfaces.IImpressao;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class Endereco implements IImpressao {
    private Integer id;
    private Integer idUsuario;

    @NotEmpty(message = "logradouro não pode ser nulo ou vazio")
    @Size(min = 1, max = 255, message = "O tamanho do logradouro deve estar entre 1 e 255 caracteres")
    private String logradouro;

    @NotEmpty(message = "numero não pode ser nulo ou vazio")
    @Size(min = 1, max = 10, message = "O tamanho do número deve estar entre 1 e 10 caracteres")
    private String numero;

    @NotEmpty(message = "complemento não pode ser nulo ou vazio")
    @Size(max = 255, message = "O tamanho do complemento deve estar no máximo 255 caracteres")
    private String complemento;

    @NotEmpty(message = "cep não pode ser nulo ou vazio")
    @Size(min = 8, max = 8, message = "O CEP deve ter exatamente 8 caracteres")
    private String cep;

    @NotEmpty(message = "cidade não pode ser nula ou vazia")
    @Size(min = 1, max = 255, message = "O tamanho da cidade deve estar entre 1 e 255 caracteres")
    private String cidade;

    @NotEmpty(message = "estado não pode ser nulo ou vazio")
    @Size(min = 1, max = 50, message = "O tamanho do estado deve estar entre 1 e 50 caracteres")
    private String estado;

    @NotEmpty(message = "país não pode ser nulo ou vazio")
    @Size(min = 1, max = 50, message = "O tamanho do país deve estar entre 1 e 50 caracteres")
    private String pais;

    @NotEmpty(message = "região não pode ser nula ou vazia")
    @Size(min = 1, max = 50, message = "O tamanho da região deve estar entre 1 e 50 caracteres")
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
