package model;

import interfaces.Impressao;

import java.util.ArrayList;

public class Empresa implements Impressao {
    private Integer idEmpresa;
    private Integer idUsuario;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;
    private ArrayList<Produto> produtos = new ArrayList<>();

    public Empresa() {
        super();
    }

    public Empresa(String nomeFantasia, String cnpj, String razaoSocial,
                   String inscricaoEstadual, String setor, Integer idUsuario) {
        this.nomeFantasia = nomeFantasia;
        this.idUsuario = idUsuario;
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
    public int getIdEmpresa() {
        return idEmpresa;
    }
    public Integer getIdUsuario() { return idUsuario; }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
            if(id == x.getIdProduto()){
                produtos.remove(x);
                return true;
            }
        }
        return false;
    }

    @Override
    public void imprimir() {
        System.out.println("ID do da Empresa: " + getIdEmpresa());
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
