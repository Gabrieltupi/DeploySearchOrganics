package modelo;

import interfaces.Impressao;
import utils.FormaPagamento;
import utils.StatusPedido;
import utils.validadores.TipoEntrega;
import utils.validadores.ValidadorCEP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pedido implements Impressao {
    private int id;
    private int usuarioId;
    private BigDecimal total;
    private FormaPagamento formaPagamento;
    private StatusPedido statusPedido;
    private Boolean entregue;
    private LocalDate dataDeEntrega;
    private Endereco endereco;
    private LocalDate inicioEntrega;
    private TipoEntrega tipoEntrega;
    private ArrayList<ProdutoCarrinho> produtos;
    private Cupom cupom;
    private BigDecimal valorFrete = new BigDecimal(0);

    public Pedido() {
    }

    public Pedido(int usuarioId, ArrayList<ProdutoCarrinho> produtos,
                  FormaPagamento formaPagamento, LocalDate dataDeEntrega,
                  Endereco endereco,
                  TipoEntrega tipoEntrega, Cupom cupom, BigDecimal total, BigDecimal frete, StatusPedido status) {
        this.cupom = cupom;
        if(cupom == null){
            cupom.setTaxaDeDesconto(new BigDecimal(0));
        }
        this.statusPedido = status;
        this.produtos = produtos;
        this.formaPagamento = formaPagamento;
        this.dataDeEntrega = dataDeEntrega;
        this.endereco = endereco;
        this.usuarioId = usuarioId;
        this.tipoEntrega = tipoEntrega;
        if(tipoEntrega == tipoEntrega.RETIRAR_NO_LOCAL){
            this.total = total.subtract(cupom.getTaxaDeDesconto());

        }else {
            this.total = total.add(calcularFrete(endereco.getCep())).subtract(cupom.getTaxaDeDesconto());
        }
        this.total = total.subtract(cupom.getTaxaDeDesconto());
        this.entregue = false;
        this.inicioEntrega = LocalDate.now();
        this.valorFrete = frete;
    }

    public int getId() {
        return id;
    }


    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Boolean getEntregue() {
        return entregue;
    }

    public void setEntregue(Boolean entregue) {
        this.entregue = entregue;
    }

    public LocalDate getDataDeEntrega() {
        return dataDeEntrega;
    }

    public void setDataDeEntrega(LocalDate dataDeEntrega) {
        this.dataDeEntrega = dataDeEntrega;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public ArrayList<ProdutoCarrinho> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<ProdutoCarrinho> produtos) {
        this.produtos = produtos;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public LocalDate getInicioEntrega() {
        return inicioEntrega;
    }

    public void setInicioEntrega(LocalDate inicioEntrega) {
        this.inicioEntrega = inicioEntrega;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public BigDecimal calcularFrete(String cep) {
        BigDecimal frete = new BigDecimal("0.00");
        String regiao = ValidadorCEP.isCepValido(cep);

        if (regiao == null) return null;

        if (regiao.equals("SP - Capital")) {
            frete = new BigDecimal("10.00");
        }
        if (regiao.equals("SP - Área Metropolitana")) {
            frete = new BigDecimal("15.00");
        }
        if (regiao.equals("SP - Litoral")) {
            frete = new BigDecimal("20.00");
        }
        if (regiao.equals("SP - Interior")) {
            frete = new BigDecimal("25.00");
        }

        return frete;
    }

    public boolean pedidoEntrege(boolean entregue){
        this.entregue = entregue;
        return entregue;
    }

    @Override
    public void imprimir() {
        String statusEntregue = entregue ? "Entregue" : "Não entregue";;

        System.out.printf("""
                ID do Pedido: %d
                Forma de pagamento: %s
                Data: %s
                Tipo de entrega: %s
                Status de entrega: %s
                CEP de entrega: %s
                """,
                id, formaPagamento, dataDeEntrega, tipoEntrega, statusEntregue, endereco.getCep());
        System.out.println("Produtos: \n");


        for (ProdutoCarrinho produto: produtos) {
            System.out.println(" Nome do produto: " + produto.getNome()
                    + " Quantidade: " + produto.getQuantidadePedida());
        }
        System.out.println("Valor total: " + total);
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }
}
