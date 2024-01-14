package modelo;
import interfaces.Impressao;
import utils.TipoAtivo;

import java.time.LocalDate;

public class Usuario implements Impressao {
    private  Integer idUsuario;
    private String nome;
    private String sobrenome ;
    private Endereco endereco;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String login;
    private String senha;
    private TipoAtivo tipoAtivo = TipoAtivo.S;

    public Usuario() {

    }

    public Usuario(String nome, String sobrenome, String cpf, LocalDate dataNascimento, String email, String login, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.login = login;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome=nome;
    }
    public String getSobrenome(){
        return sobrenome;
    }
    public void setSobrenome(String sobrenome){
        this.sobrenome=sobrenome;
    }
    public Endereco getEndereco(){
        return endereco;
    }
    public void setEndereco(Endereco endereco){
        this.endereco=endereco;
    }
    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf=cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoAtivo getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(TipoAtivo tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }

    @Override
    public void imprimir() {
        System.out.println("ID do Usuário: " + idUsuario);
        System.out.println("Nome "+ getNome() + " Sobrenome "+ getSobrenome());
        System.out.println("Data de nascimento " + getDataNascimento());
    }
}
