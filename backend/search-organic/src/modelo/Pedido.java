package modelo;

import interfaces.Impressao;
import utils.FormaPagamento;
import utils.validadores.TipoEntrega;
import utils.validadores.ValidadorCEP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Pedido implements Impressao {
    private static int pedidoId = 1;
    private int id;
    private Map<Integer, Produto> produtos = new HashMap<>();
    private Map<Integer, BigDecimal> quantidadeProduto = new HashMap<>();
    private BigDecimal total;
    private FormaPagamento formaPagamento;
    private Boolean entregue;
    private LocalDate dataDeEntrega;
    private Endereco endereco;
    private int consumidorId;
    private LocalDate inicioEntrega;
    private TipoEntrega tipoEntrega;

    public Pedido(int consumidorId, Map<Integer, Produto> produtos,
                  Map<Integer, BigDecimal> quantidadeProduto,
                  FormaPagamento formaPagamento, LocalDate dataDeEntrega,
                  Endereco endereco,
                  TipoEntrega tipoEntrega, Cupom cupom, BigDecimal total) {
        if(cupom == null){
            cupom.setTaxaDeDesconto(new BigDecimal(0));
        }

        this.id = pedidoId;
        this.produtos = produtos;
        this.quantidadeProduto = quantidadeProduto;
        this.formaPagamento = formaPagamento;
        this.dataDeEntrega = dataDeEntrega;
        this.endereco = endereco;
        this.consumidorId = consumidorId;
        this.tipoEntrega = tipoEntrega;
        if(tipoEntrega == tipoEntrega.RETIRAR_NO_LOCAL){
            this.total = total.subtract(cupom.getTaxaDeDesconto());

        }else {
            this.total = total.add(calcularFrete(endereco.getCep())).subtract(cupom.getTaxaDeDesconto());
        }
        this.total = total.add(calcularFrete(endereco.getCep())).subtract(cupom.getTaxaDeDesconto());
        this.entregue = false;
        this.inicioEntrega = LocalDate.now();
        pedidoId++;
    }


    public int getId() {
        return id;
    }

    public Map<Integer, Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Map<Integer, Produto> produtos) {
        this.produtos = produtos;
    }

    public Map<Integer, BigDecimal> getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Map<Integer, BigDecimal> quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
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

    public int getConsumidorId() {
        return consumidorId;
    }

    public void setConsumidorId(int consumidorId) {
        this.consumidorId = consumidorId;
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
        System.out.printf("""
                ID do Pedido: %d
                Forma de pagamento: %s
                Data: %s
                Tipo de entrega: %s
                Status de entraga: %b%n
                CEP de entrega: %s
                """,
                id, formaPagamento, dataDeEntrega, tipoEntrega, entregue, endereco.getCep());
        System.out.println("Produtos: \n");

        for (int key : produtos.keySet()) {
            System.out.println(" Nome do produto: " + produtos.get(key).getNome()
                    + " Quantidade: " + quantidadeProduto.get(key));
        }
        System.out.println("Valor total: " + total);
    }
}
