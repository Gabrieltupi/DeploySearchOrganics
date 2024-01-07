package modelo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private static int idCarrinho = 1;
    private int id;
    private int idEmpresa;
    private Map<Integer, Produto> produtos = new HashMap<>();
    private Usuario consumidor;

    public Carrinho(Produto produto, BigDecimal quantidade) {
        this.id = idCarrinho;
        this.produtos.put(produto.getIdProduto(), produto);
        idCarrinho++;
    }

    public Carrinho(Map<Integer, Produto> produtos, Map<Integer, BigDecimal> quantidadeProduto, Usuario consumidor, Produto verifica) {
        this.id = idCarrinho;
        this.produtos = produtos;
        this.consumidor = consumidor;
        idCarrinho++;
    }

    public static int getIdCarrinho() {
        return idCarrinho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Map<Integer, Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Map<Integer, Produto> produtos) {
        this.produtos = produtos;
    }

    public Usuario getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Usuario consumidor) {
        this.consumidor = consumidor;
    }
}
