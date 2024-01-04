package Modelo;

import Interface.Impressao;
import java.util.ArrayList;

public class Empresa implements Impressao {
    private String id;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;

    private ArrayList<String> produtos = new ArrayList<>();
    private String usuario;


    public Empresa(String id, String nomeFantasia, String cnpj, String razaoSocial, String inscricaoEstadual,
                   String setor, ArrayList<String> produtos, String usuario) {
        this.id = id;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.inscricaoEstadual = inscricaoEstadual;
        this.setor = setor;

        this.produtos = produtos;
        this.usuario = usuario;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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

    public ArrayList<String> getProdutos() { return produtos; }
    public void setProdutos(ArrayList<String> produtos) { this.produtos = produtos; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public void imprimir() {
        System.out.println("Nome da empresa: " + getNomeFantasia());
        System.out.println("ID da empresa: " + getId());
        System.out.println("CNPJ da empresa: " + getCnpj());
        System.out.println("Razao Social da empresa: " + getRazaoSocial());
        System.out.println("Incriçao Social da empresa: " + getInscricaoEstadual());
        System.out.println("Setor da empresa: " + getSetor());
        System.out.println("Usuario da empresa: " + getUsuario());

        if (produtos.isEmpty()) {
            System.out.println("A empresa não possui produtos.");
        } else {
            System.out.println("Lista de Produtos:");
            for (String produto : produtos) {
                System.out.println(produto);
            }
        }
    }
}
