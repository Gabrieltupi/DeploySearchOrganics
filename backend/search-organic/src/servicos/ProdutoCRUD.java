package servicos;
import modelo.Produto;
import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.util.ArrayList;
import java.math.BigDecimal;

public class ProdutoCRUD {
    private ArrayList<Produto> produtos;

    public ProdutoCRUD() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void listarProdutos() {
        for (Produto produto : produtos) {
            produto.imprimir();
        }
    }

    public Produto buscarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            System.out.println("Verificando Produto por Id:" + produto.getIdProduto());
            if (produto.getIdProduto() == id) {
                System.out.println("Produto encontrado:" + produto.getIdProduto());
                produto.imprimir();
                return produto;
            }
        }
        System.out.println("Produto com ID " + id + " não encontrado");
        return null;
    }

    public void listarProdutosPorCategoria(TipoCategoria categoria) {
        for (Produto produto : produtos) {
            if (produto.getCategoria().equals(categoria)) {
                produto.imprimir();
            }
        }
    }

    public void atualizarProduto(int id, String novoNome, String novaDescricao, BigDecimal novoPreco, BigDecimal novaQuantidade,
                                 TipoCategoria novaCategoria, double novaTaxa, UnidadeMedida novaUnidadedeMedida) {
        for (Produto produto : produtos) {
            if (produto.getIdProduto() == id) {
                System.out.println("Produto encontrado, atualize as informações: " + produto.getIdProduto());
                produto.setNome(novoNome);
                produto.setDescricao(novaDescricao);
                produto.setPreco(novoPreco);
                produto.setQuantidade(novaQuantidade);
                produto.setCategoria(novaCategoria);
                produto.setTaxa(novaTaxa);
                produto.setUnidadeMedida(novaUnidadedeMedida);
                System.out.println("Produto atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Produto não pode ser atualizado");
    }

    public void deletarProduto(int id) {
        for (Produto produto : produtos) {
            if (produto.getIdProduto() == id) {
                produtos.remove(produto);
                return;
            }
        }
        System.out.println("Produto não pode ser encontrado em nosso Sistema");

    }

}













