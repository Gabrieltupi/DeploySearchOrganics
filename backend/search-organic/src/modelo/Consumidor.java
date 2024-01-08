package modelo;

import interfaces.Impressao;

import java.time.LocalDate;

public class Consumidor extends Usuario  implements Impressao {

    private String cpf;

    public Consumidor(String login, String password, String nome, String sobrenome, Endereco endereco, LocalDate dataNascimento, String cpf) {
        super(login, password, nome, sobrenome, endereco, dataNascimento);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public void imprimir() {
        System.out.println("ID do Usuário: " + getId());
        System.out.println("Nome "+ getNome() + " Sobrenome "+ getSobrenome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Data de nascimento " + getDataNascimento());
        System.out.println("Status: " + verificarStatus());
    }
}