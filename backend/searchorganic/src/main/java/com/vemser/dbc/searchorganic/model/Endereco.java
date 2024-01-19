package com.vemser.dbc.searchorganic.model;


import com.vemser.dbc.searchorganic.interfaces.IImpressao;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;

public class Endereco implements IImpressao {
    private Integer id;
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

    public Endereco(){}

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getPais() {
        return pais;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setCep(String cep) {
        this.cep = cep;
        this.regiao = ValidadorCEP.isCepValido(cep);
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
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

    @Override
    public String toString() {
        return "ID: " + id +
                "\nRegiao: " + regiao +
                "\nLogradouro: " + logradouro +
                "\nNúmero: " + numero +
                "\nComplemento: " + complemento +
                "\nCEP: " + cep +
                "\nCidade: " + cidade +
                "\nEstado: " + estado +
                "\nPaís: " + pais + "\n";
    }

    public void setId(Integer proximoId) {
        this.id = proximoId;
    }
}
