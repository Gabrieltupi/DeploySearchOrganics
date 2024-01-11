package modelo;
//
//import interfaces.Impressao;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//public class DadosPessoais implements Impressao {
//    private static int dadosPessoaisId = 1;
//    private int id;
//    private String nome;
//    private String sobrenome;
//    private Endereco endereco;
//    private LocalDate dataNascimento;
//
//    public DadosPessoais(String nome, String sobrenome, Endereco endereco, LocalDate dataNascimento) {
//        this.id = dadosPessoaisId;
//        this.nome = nome;
//        this.sobrenome = sobrenome;
//        this.endereco = endereco;
//        this.dataNascimento = dataNascimento;
//        dadosPessoaisId++;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public String getSobrenome() {
//        return sobrenome;
//    }
//
//    public void setSobrenome(String sobrenome) {
//        this.sobrenome = sobrenome;
//    }
//
//    public Endereco getEndereco() {
//        return endereco;
//    }
//
//    public void setEndereco(Endereco endereco) {
//        this.endereco = endereco;
//    }
//
//    public LocalDate getDataNascimento() {
//        return dataNascimento;
//    }
//
//    public void setDataNascimento(LocalDate dataNascimento) {
//        this.dataNascimento = dataNascimento;
//    }
//
//    @Override
//    public void imprimir() {
//        System.out.println("ID dos dados pessoais: " + getId());
//        System.out.println("Nome dos dados pessoais: " + getNome() + " Sobrenome dos dados pessoais: " + getSobrenome());
//        System.out.println("Data de nascimento dos dados pessoais: " + getDataNascimento());
//        System.out.println("Endereco dos dados pessoais: " + getEndereco());
//    }
//
//    @Override
//    public String toString() {
//        return "Nome completo: " + nome + " " + sobrenome +
//                "\nEndereço: " + endereco +
//                "\nData de Nascimento: " + endereco + "\n";
//    }
//}