package modelo;

import interfaces.Impressao;

import java.time.LocalDate;
import java.util.ArrayList;

public class Empresa extends Usuario implements Impressao {

    private static int proximoId = 1;
    private String nomeFantasia
            ;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;
    private int id_empresa;

    private ArrayList<Produto> produtos = new ArrayList<>();


    public Empresa(String login, String password, String nome, String sobrenome, Endereco endereco,
                   LocalDate dataNascimento, String nomeFantasia, String cnpj, String razaoSocial,
                   String inscricaoEstadual, String setor) {
        super(nome, sobrenome, endereco,dataNascimento,login, password);
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.inscricaoEstadual = inscricaoEstadual;
        this.setor = setor;
    }
    public Empresa(String login, String password, String nome,String cpf, String email, String sobrenome, Endereco endereco,
                   LocalDate dataNascimento, String nomeFantasia, String cnpj, String razaoSocial,
                   String inscricaoEstadual, String setor) {
        super(nome, sobrenome, endereco,cpf, dataNascimento,email,login, password);;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.inscricaoEstadual = inscricaoEstadual;
        this.setor = setor;
    }
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getInscricaoEstadual() { return inscricaoEstadual; }
    public void setInscricaoEstadual(String inscricaoEstadual) { this.inscricaoEstadual = inscricaoEstadual; }
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    private static synchronized int gerarProximoId() {
        return proximoId++;
    }

    public ArrayList<Produto> getProdutos() { return produtos; }
    public void setProdutos(ArrayList<Produto> produtos) { this.produtos = produtos; }
    public boolean adicionarProduto(Produto produto){
        if(produto != null){
            produtos.add(produto);
            return true;
        }
        return false;
    }

    public boolean adicionarProduto(ArrayList<Produto> produto){
        if(produto != null){
            produtos = produto;
            return true;
        }
        return false;
    }

    public boolean removerProduto(int id){
        for(Produto x: produtos){
            if(id == x.getId_Produto()){
                produtos.remove(x);
                return true;
            }
        }
        return false;
    }

    @Override
    public void imprimir() {
        System.out.println("ID do da Empresa: " + getId_empresa());
        System.out.println("Nome "+ getNome() + " Sobrenome "+ getSobrenome());
        System.out.println("Status: " + verificarStatus());
        System.out.println("Data de nascimento " + getDataNascimento());

        System.out.println("\nDados da Empresa: \n");
        System.out.println("Nome da empresa: " + getNomeFantasia());
        System.out.println("CNPJ da empresa: " + getCnpj());
        System.out.println("Razao Social da empresa: " + getRazaoSocial());
        System.out.println("Incriçao Social da empresa: " + getInscricaoEstadual());
        System.out.println("Setor da empresa: \n" + getSetor());

        if (produtos.isEmpty()) {
            System.out.println("A empresa não possui produtos.");
        } else {
            System.out.println("Lista de Produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }
}
