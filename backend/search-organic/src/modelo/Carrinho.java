package modelo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private int idEmpresa;
    private Map<Integer, Produto> produtos = new HashMap<>();
    private Map<Integer, BigDecimal> quantidadeProduto = new HashMap<>();
    private BigDecimal quantidade = new BigDecimal(0);
    private BigDecimal valorTotal = new BigDecimal(0);

    private Usuario usuario;

    public Carrinho(Usuario usuario, int idEmpresa) {
        this.idEmpresa = idEmpresa;
        this.usuario = usuario;

    }

    public boolean adicionarProdutoAoCarrinho(Produto produto, BigDecimal quantidade) {
        if (produto != null && idEmpresa == produto.getEmpresaId() &&
                produto.getQuantidade().compareTo(quantidade) > 0) {
            this.produtos.put(produto.getIdProduto(), produto);
            this.quantidadeProduto.put(produto.getIdProduto(), quantidade);
            valorTotal = valorTotal.add(produto.getPreco().multiply(quantidade));
            return true;
        } else {
            System.out.println("Produto não é valido para essa compra!!");
            return false;
        }
    }

    public boolean editarQuantidadeProdutoDaSacola(int id, BigDecimal quantidade) {
        if (produtos.get(id) != null) {
            quantidadeProduto.put(id, quantidade);
            return true;
        }
        System.out.println("Produto não encontrado!");
        return false;
    }

    public boolean removerProdutoDoCarrinho(int id) {
        if (produtos.remove(id) != null) {
            return true;
        }
        System.out.println("ID não encontrado!!");
        return false;

    }

    public void listarProdutosDoCarrinho() {
        for (int key : produtos.keySet()) {
            System.out.println("Número: " + key + " Nome do produto: " + produtos.get(key).getNome()
                    + " Quantidade: " + quantidadeProduto.get(key));
        }
    }

    public void limparSacola() {
        quantidadeProduto = new HashMap<>();
        produtos = new HashMap<>();
    }
}