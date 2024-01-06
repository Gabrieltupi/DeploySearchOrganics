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

    Produto verifica;

    public Carrinho(int idEmpresa, Produto produto, BigDecimal quantidade){
        this.idEmpresa = idEmpresa;
        if(produto != null && idEmpresa == produto.getEmpresaId() &&
                produto.getQuantidade().compareTo(quantidade) > 0){
            this.produtos.put(produto.getIdProduto(), produto);
            this.quantidadeProduto.put(produto.getIdProduto(), quantidade);
            valorTotal = valorTotal.add(produto.getPreco().multiply(quantidade));
        } else {
            System.out.println("Produto não cadastrado!");
        }
    }

    public boolean adicionarProdutoAoCarrinho(Produto produto, BigDecimal quantidade){
        if(produto != null && idEmpresa == produto.getEmpresaId() &&
                produto.getQuantidade().compareTo(quantidade) > 0){
            this.produtos.put(produto.getIdProduto(), produto);
            this.quantidadeProduto.put(produto.getIdProduto(), quantidade);
            valorTotal = valorTotal.add(produto.getPreco().multiply(quantidade));
            return true;
        } else {
            System.out.println("Produto não é valido para essa compra!!");
            return false;
        }
    }

    public boolean editarQuantidadeProdutoDaSacola(int id, BigDecimal quantidade){
        verifica = null;
        verifica = produtos.get(id);
        if(verifica != null){
            quantidadeProduto.put(id, quantidade);
            return true;
        }
        System.out.println("Produto não encontrado!");
        return false;
    }

    public boolean removerProdutoDoCarrinho(int id){
        verifica = null;
        verifica = produtos.remove(id);
        quantidadeProduto.remove(id);

        if(verifica != null){
            return true;
        }
        System.out.println("ID não encontrado!!");
        return false;

    }

    public void listarProdutosDoCarrinho(){
        for(int key: produtos.keySet()){
            System.out.println("Número: " + key + " Nome do produto: " + produtos.get(key).getNome()
                    + " Quantidade: " + quantidadeProduto.get(key));
        }
    }

    public void limparSacola(){
        quantidadeProduto = new HashMap<>();
        produtos = new HashMap<>();
    }
}
