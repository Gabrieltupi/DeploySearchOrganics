package modelo;
import utils.UnidadeMedida;
import interfaces.CupomServicos;
import interfaces.Impressao;
import java.math.BigDecimal;

public class Cupom implements Impressao, CupomServicos {
    private static int cupomIdCounter = 1;
    private final int cupomId;
    private String nomeProduto;
    private boolean ativo;
    private String descricao;
    private double taxaDeDesconto;

    public Cupom(int cupomId, String nomeProduto, boolean ativo, String descricao, double taxaDeDesconto) {
        this.cupomId = cupomId;
        this.nomeProduto = nomeProduto;
        this.ativo = ativo;
        this.descricao = descricao;
        this.taxaDeDesconto = taxaDeDesconto;
    }

    @Override
    public void imprimir() {
        System.out.println("\nInformações sobre o cupom de desconto:");
        System.out.println("ID do cupom: " + getCupomId());
        System.out.println("Produto associado: " + getNomeProduto());
        System.out.println("Descrição do cupom: " + getDescricao());
        System.out.println("Taxa de desconto: " + this.taxaDeDesconto);
    }

    @Override
    public boolean eValido() {
        if (this.ativo) {
            System.out.println("O cupom é válido.");
            return true;
        } else {
            System.out.println("O cupom é inválido.");
            return false;
        }
    }

    @Override
    public void ativarCupom() {
        if (this.ativo) {
            System.out.println("O cupom já está ativo!");
        } else {
            this.ativo = true;
            System.out.println("O cupom foi ativado!");
        }
    }

    @Override
    public boolean desativarCupom() {
        if (this.ativo) {
            this.ativo = false;
            System.out.println("O cupom agora está inativo.");
            return true;
        } else {
            System.out.println("O cupom já está inativo.");
            return false;
        }
    }

    @Override
    public void validarCupom() {
    }


    public static int getCupomIdCounter() {
        return cupomIdCounter;
    }

    public static void setCupomIdCounter(int cupomIdCounter) {
        Cupom.cupomIdCounter = cupomIdCounter;
    }

    public int getCupomId() {
        return cupomId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getTaxaDeDesconto() {
        return taxaDeDesconto;
    }

    public void setTaxaDeDesconto(double taxaDeDesconto) {
        this.taxaDeDesconto = taxaDeDesconto;
    }
}

