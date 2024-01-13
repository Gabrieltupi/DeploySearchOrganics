package modelo;

import interfaces.Impressao;
import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.math.BigDecimal;

public class Produto implements Impressao {
    private Integer id_Produto;
    private int id_empresa;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private BigDecimal quantidade;
    private Empresa empresa;
    private TipoCategoria categoria;
    private double taxa;
    private UnidadeMedida unidadeMedida;



    public Produto(String nome, String descricao, BigDecimal preco,
                   BigDecimal quantidade, TipoCategoria categoria, double taxa,
                   UnidadeMedida unidadeMedida, Empresa empresa) {
        this.empresa = empresa;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.taxa = taxa;
        this.unidadeMedida = unidadeMedida;
    }


    public int getIdEmpresa() {
        return empresa != null ? empresa.getId_empresa() : 0;
    }


    public Empresa getEmpresa() {
        return empresa;
    }

    public int getId_Produto() {
        return id_Produto;
    }

    public void setId_Produto(Integer id_Produto) {
        this.id_Produto = id_Produto;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        if (quantidade.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Não foi possível realizar a compra. Temos apenas " + this.getQuantidade() + " unidades em estoque");
            return;
        }
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria.toString();
    }
    public TipoCategoria getCategoriaT() {
        return categoria;
    }

    public void setCategoria(TipoCategoria categoria) {
        this.categoria = categoria;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    @Override
    public void imprimir() {
        System.out.println("ID do produto: " + getId_Produto());
        System.out.println("ID da empresa: " + getId_empresa());
        System.out.println("Categoria do produto: " + getCategoria());
        System.out.print("Nome do produto: " + getNome());
        System.out.print("   Preço do produto: R$: " + getPreco());
        System.out.print("   Quantidade do produto: " + getQuantidade());
        System.out.println(getUnidadeMedida());
        System.out.println("Descrição do produto: " + getDescricao());
        System.out.println("Categoria do produto: " + getCategoria());
        System.out.println("Taxa de servico: " + getTaxa());
        System.out.println("-------------------------------------------------------------");
    }
}
