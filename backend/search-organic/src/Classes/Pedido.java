package Classes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Pedido {
    private final UUID id;
    private ArrayList<Produto> produtos;
    private BigDecimal total;
    private String formaPagamento;
    private Boolean entregue;
    private LocalDate data;


    public Pedido(ArrayList<Produto> produtos, String formaPagamento) {
        this.id = UUID.randomUUID();
        this.produtos = produtos;
        this.total = this.calcularTotal();
        this.formaPagamento = formaPagamento;
        this.entregue = false;
        this.data = LocalDate.now();
    }

    private BigDecimal calcularTotal(){
        BigDecimal resultado = new BigDecimal(0);
        for (Produto produto : produtos) {
            BigDecimal quantidade = new BigDecimal(produto.getQuantidade());
            BigDecimal preco = produto.getPreco();
            resultado = resultado.add(quantidade.multiply(preco));
        }

        return resultado;
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Boolean getEntregue() {
        return entregue;
    }

    public void setEntregue(Boolean entregue) {
        this.entregue = entregue;
    }

    public LocalDate getData() {
        return data;
    }
}
