package modelo;

import java.util.UUID;

public class Endereco {
    private static int enderecoId = 1;
    private int id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;

    public Endereco(String logradouro, String numero, String complemento, String cep, String cidade,
                    String estado, String pais) {
        this.id = enderecoId;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        enderecoId++;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return "ID: " + id +
                "\nLogradouro: " + logradouro +
                "\nNúmero: " + numero +
                "\nComplemento: " + complemento +
                "\nCEP: " + cep +
                "\nCidade: " + cidade +
                "\nEstado: " + estado +
                "\nPaís: " + pais + "\n";
    }
}
