package modelo;

import interfaces.Impressao;
import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.math.BigDecimal;

public class Produto implements Impressao {
    private static int produtoId = 1;
    private int idProduto;
    private int empresaId;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private BigDecimal quantidade;
    private TipoCategoria categoria;
    private double taxa;
    private UnidadeMedida unidadeMedida;

    public Produto(int empresaId, String nome, String descricao, BigDecimal preco,
                   BigDecimal quantidade, TipoCategoria categoria, double taxa,
                   UnidadeMedida unidadeMedida) {
        this.idProduto = produtoId;
        this.empresaId = empresaId;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.taxa = taxa;
        this.unidadeMedida = unidadeMedida;
        this.produtoId++;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getEmpresaId(){
        return empresaId;
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
        this.quantidade = quantidade;
    }

    public TipoCategoria getCategoria() {
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
        System.out.println("ID do produto: " + getIdProduto());
        System.out.println("ID da empresa: " + getEmpresaId());
        System.out.println("Nome do produto: " + getNome());
        System.out.println("Descrição do produto: " + getDescricao());
        System.out.println("Preço do produto: " + getPreco());
        System.out.println("Quantidade do produto: " + getQuantidade());
        System.out.println("Categoria do produto: " + getCategoria());
        System.out.println("Taxa do produto: " + getTaxa());
        System.out.println("Unidade de medida do produto: " + getUnidadeMedida());
    }
}
