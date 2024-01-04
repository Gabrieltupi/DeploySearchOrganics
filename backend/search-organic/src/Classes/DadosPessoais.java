package Classes;
import Modelo.Endereco;

import java.util.Date;

public class DadosPessoais {
    private static int dadosPessoaisId = 1;
    private int id;
    private String nome;
    private String sobrenome;
    private Endereco endereco;
    private Date dataNascimento;

    public DadosPessoais(String nome, String sobrenome, Endereco endereco, Date dataNascimento) {
        this.id = dadosPessoaisId;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        dadosPessoaisId++;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}










