package modelo;

import interfaces.Impressao;

public class Consumidor  implements Impressao {
    private static int consumidorId = 1;
    private int id;
    private String cpf;
    private Usuario usuario;

    public Consumidor(String cpf, Usuario usuario) {
        this.id = consumidorId;
        this.cpf = cpf;
        this.usuario = usuario;
        consumidorId++;
    }

    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void imprimir() {
        System.out.println("CPF do consumidor: " + getCpf());
        System.out.println("ID do consumidor: " + getId());
        System.out.println("Usuário do consumidor: " + getUsuario());
    }
}
