package modelo;

import interfaces.Impressao;
import java.util.ArrayList;

public class Empresa implements Impressao {
    private static int empresaId = 1;
    private int idEmpresa;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;

    private ArrayList<Produto> produtos = new ArrayList<>();
    private Usuario usuario;


    public Empresa(String nomeFantasia, String cnpj, String razaoSocial, String inscricaoEstadual,
                   String setor, Usuario usuario) {
        this.idEmpresa = empresaId;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.inscricaoEstadual = inscricaoEstadual;
        this.setor = setor;
        this.usuario = usuario;

        empresaId++;
    }

    public int getId() { return idEmpresa; }
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

    public ArrayList<Produto> getProdutos() { return produtos; }
    public void setProdutos(ArrayList<Produto> produtos) { this.produtos = produtos; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

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
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }
}
