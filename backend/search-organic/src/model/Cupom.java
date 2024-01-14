package model;
import utils.TipoAtivo;
import interfaces.CupomServicos;
import interfaces.Impressao;
import java.math.BigDecimal;

public class Cupom implements Impressao, CupomServicos {
    private Integer cupomId;
    private String nomeCupom;
    private TipoAtivo ativo;
    private String descricao;
    private BigDecimal taxaDeDesconto;
    private Integer idEmpresa;

    public Cupom(){}

    public Cupom(int cupomId, String nomeCupom, TipoAtivo ativo, String descricao, BigDecimal taxaDeDesconto, Integer idEmpresa) {
        this.cupomId = cupomId;
        this.nomeCupom = nomeCupom;
        this.ativo = ativo;
        this.descricao = descricao;
        this.taxaDeDesconto = taxaDeDesconto;
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public void imprimir() {
        System.out.println("\nInformações sobre o cupom de desconto:");
        System.out.println("ID do cupom: " + getCupomId());
        System.out.println("Produto associado: " + getNomeCupom());
        System.out.println("Descrição do cupom: " + getDescricao());
        System.out.println("Taxa de desconto: " + this.taxaDeDesconto);
    }

    @Override
    public boolean eValido() {
        if (this.ativo == TipoAtivo.S) {
            System.out.println("O cupom é válido.");
            return true;
        } else {
            System.out.println("O cupom é inválido.");
            return false;
        }

    }

    @Override
    public void ativarCupom() {
        if (this.ativo.getStatus().equals("S")) {
            System.out.println("O cupom já está ativo!");
        } else {
            this.ativo = TipoAtivo.S;
            System.out.println("O cupom foi ativado!");
        }
    }

    @Override
    public boolean desativarCupom() {
        if (this.ativo.getStatus().equals("S")) {
            this.ativo = TipoAtivo.N;
            System.out.println("O cupom agora está inativo.");
            return true;
        } else {
            System.out.println("O cupom já está inativo.");
            return false;
        }
    }


    public Integer getCupomId() {
        return cupomId;
    }
    public void setCupomId(Integer cupomId){
        this.cupomId = cupomId;
    }

    public String getNomeCupom() {
        return nomeCupom;
    }

    public void setNomeCupom(String nomeProduto) {
        this.nomeCupom = nomeProduto;
    }

    public String isAtivo() {
        return ativo.getStatus();
    }

    public TipoAtivo getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = TipoAtivo.fromString(ativo);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getTaxaDeDesconto() {
        return taxaDeDesconto;
    }

    public void setTaxaDeDesconto(BigDecimal taxaDeDesconto) {
        this.taxaDeDesconto = taxaDeDesconto;
    }
}

