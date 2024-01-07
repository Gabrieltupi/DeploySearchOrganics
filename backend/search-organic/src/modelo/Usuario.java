package modelo;
import interfaces.Impressao;

import java.time.LocalDate;
import java.util.Date;
public class Usuario extends DadosPessoais implements Impressao {
    private int usuarioId = 1;
    private String login;
    private String password;
    private boolean ativo = true;


    public Usuario(String login, String password, String nome, String sobrenome, Endereco endereco, LocalDate dataNascimento) {
        super(nome, sobrenome, endereco, dataNascimento);
        this.login = login;
        this.password = password;
        usuarioId++;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String verificarStatus() {
        if (ativo) {
            return "Usuário online";
        } else {
            return "Usuário offline";
        }
    }

    @Override
    public void imprimir() {
        System.out.println("Nome "+ getNome());
        System.out.println("Sobrenome "+ getSobrenome());
        System.out.println("ID do Usuário: " + usuarioId);
        System.out.println("Login: " + login);
        System.out.println("Status: " + verificarStatus());
        System.out.println("Endereço "+ getEndereco());
        System.out.println("Data de nascimento "+getDataNascimento());
        System.out.println("-------------------");
    }
}
