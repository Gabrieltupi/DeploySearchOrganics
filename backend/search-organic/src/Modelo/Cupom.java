package modelo;

import interfaces.Impressao;

public class Cupom  implements Impressao {
    private static int cupomId = 1;
    private int id;
    private String nomeProduto;

    private String descricao;
    private double taxaDeDesconto;

    public Cupom(String nomeProduto, String descricao, double taxaDeDesconto) {
        this.id = cupomId;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.taxaDeDesconto = taxaDeDesconto;
        cupomId++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
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

    public boolean validarCupom() {
        return taxaDeDesconto > 0 && nomeProduto !=null && !nomeProduto.isEmpty()
                && taxaDeDesconto <= 20.0;
    }


    public void imprimir() {
        System.out.println("\n O que você precisa saber sobre seu cupom de desconto:");
        System.out.println("\n O seu ID é : " + id);
        System.out.println("\n O seu Produto é : " + nomeProduto);
        System.out.println("\n Descrição do item : " + descricao);
        System.out.println("\n A sua taxa de desconto é : " + taxaDeDesconto);


    }

}
