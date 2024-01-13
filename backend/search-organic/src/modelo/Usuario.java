package modelo;
import interfaces.Impressao;
import java.time.LocalDate;

public class Usuario implements Impressao {
    private  Integer idUsuario;
    private int idEndereco;
    private String nome;
    private String sobrenome ;
    private Endereco endereco;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String login;
    private String password;
    private boolean ativo = true;

    public Usuario() {

    }

    public Usuario(String nome, String sobrenome, Endereco endereco, LocalDate dataNascimento, String login, String password){
        this.nome = this.nome;
        this.sobrenome = this.sobrenome;
        this.endereco = this.endereco;
        this.cpf = cpf;
        this.dataNascimento = this.dataNascimento;
        this.email = email;
        this.login = this.login;
        this.password = this.password;
        this.ativo = true;

    }
    public Usuario(String nome, String sobrenome, Endereco endereco, String cpf, LocalDate dataNascimento, String email, String login, String password) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.login = login;
        this.password = password;
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
    public int getIdEndereco() {
        return idEndereco;
    }
    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
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

        System.out.println("ID do Usuário: " + idUsuario);
        System.out.println("Nome "+ getNome() + " Sobrenome "+ getSobrenome());
        System.out.println("Status: " + verificarStatus());
        System.out.println("Data de nascimento " + getDataNascimento());
    }
}
