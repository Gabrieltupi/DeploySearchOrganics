package modelo;

import interfaces.Impressao;
import utils.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido implements Impressao {
    private static int pedidoId = 1;
    private int id;
    private ArrayList<Produto> produtos;
    private BigDecimal total;
    private FormaPagamento formaPagamento;
    private Boolean entregue;
    private LocalDate data;

    private int consumidorId;


    public Pedido(ArrayList<Produto> produtos, FormaPagamento formaPagamento, int consumidorId) {
        this.id = pedidoId;
        this.produtos = produtos;
        this.total = this.calcularTotal();
        this.formaPagamento = formaPagamento;
        this.entregue = false;
        this.data = LocalDate.now();
        this.consumidorId = consumidorId;
        this.pedidoId++;
    }

    private BigDecimal calcularTotal(){
        BigDecimal resultado = new BigDecimal(0);
        for (Produto produto : produtos) {
            BigDecimal quantidade = produto.getQuantidade();
            BigDecimal preco = produto.getPreco();
            resultado = resultado.add(quantidade.multiply(preco));
        }

        return resultado;
    }

    public int getId() {
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

    public LocalDate getData() {
        return data;
    }

    @Override
    public void imprimir() {
        System.out.printf("""
                ID do Pedido: %d
                Entregue: %b
                Forma de pagamento: %s
                Data: %s
                Total: R$ %.2f         
                """,
                id, entregue, formaPagamento, data, total);
        System.out.println("Produtos: ");
        for(Produto produto: this.produtos){
            System.out.printf("""
                    Nome: %s
                    Quantidade: %.2f
                    Preço: R$ %.2f
                    """, produto.getNome(), produto.getQuantidade(),  produto.getPreco());
        }
    }
}
