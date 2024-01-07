package servicos;

import modelo.Carrinho;
import modelo.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class CarrinhoCRUD {

    ArrayList<Carrinho> carrinhos = new ArrayList<>();

    Produto verifica;

    public boolean adicionarProdutoAoCarrinho(Produto produto, BigDecimal quantidade){
        if(produto != null){
            carrinhos.add(new Carrinho(produto, quantidade));
            return true;
        }
        System.out.println("Produto não encontrado!");
        return false;
    }

    public boolean editarQuantidadeProdutoDaSacola(int id, BigDecimal quantidade){

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
