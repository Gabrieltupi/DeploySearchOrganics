package servicos;

import modelo.Produto;
import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.math.BigDecimal;
import java.util.ArrayList;

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
            System.out.println("-----------------");
            produto.imprimir();
            System.out.println("-----------------");
        }
    }

    public Produto buscarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getIdProduto() == id) {
                System.out.println("Produto encontrado:" + produto.getIdProduto());
                produto.imprimir();
                System.out.println("-----------------");
                return produto;
            }
        }
        System.out.println("Produto com ID " + id + " não encontrado");
        return null;
    }

    public void listarProdutosPorCategoria(TipoCategoria categoria) {
        for (Produto produto : produtos) {
            if (produto.getCategoriaT().equals(categoria)) {
                produto.imprimir();
            }
        }
    }

    public void atualizarProduto(int id, String novoNome, String novaDescricao, BigDecimal novoPreco, BigDecimal novaQuantidade,
                                 TipoCategoria novaCategoria, double novaTaxa, UnidadeMedida novaUnidadeMedida) {
        for (Produto produto : produtos) {
            if (produto.getIdProduto() == id) {
                System.out.println("Produto encontrado, atualize as informações: " + produto.getIdProduto());
                produto.setNome(novoNome);
                produto.setDescricao(novaDescricao);
                produto.setPreco(novoPreco);
                produto.setQuantidade(novaQuantidade);
                produto.setCategoria(novaCategoria);
                produto.setTaxa(novaTaxa);
                produto.setUnidadeMedida(novaUnidadeMedida);
                System.out.println("Produto atualizado com sucesso!");
                return;
            }
        }
        System.out.println("Produto não pode ser atualizado");
    }

    public void deletarProduto(int id) {
        Produto produtoRemover = null;
        for (Produto produto : produtos) {
            if (produto.getIdProduto() == id) {
                produtoRemover = produto;
                break;
            }
        }
        if (produtoRemover != null) {
            produtos.remove(produtoRemover);
        } else {
            System.out.println("Produto não pode ser encontrado em nosso Sistema");
        }
    }

    public void listarProdutosLoja(int idLoja) {
        for (Produto produto : produtos) {
            if (idLoja == produto.getEmpresaId()) {
                System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " quantidade: " + produto.getQuantidade());
                System.out.println("Descrição: " + produto.getDescricao());
                System.out.println();
            }
        }
    }
}