package servicos;

import modelo.Produto;
import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProdutoService {
    private ArrayList<Produto> produtos;

    public ProdutoService() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        try {
            produtos.add(produto);
            System.out.println("Produto adicionado com sucesso!");
        } catch (Exception e){
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listarProdutos() {
        try {
        for (Produto produto : produtos) {
            System.out.println("-----------------");
            produto.imprimir();
            System.out.println("-----------------");
        }
    } catch (Exception e) {
            System.out.println(" Erro ao listar produtos" + e.getMessage());
            e.printStackTrace();
        }
    }

    public Produto buscarProdutoPorId(int id) {
        try {
            for (Produto produto : produtos) {
                if (produto.getId_Produto() == id) {
                    System.out.println("Produto encontrado:" + produto.getId_Produto());
                    produto.imprimir();
                    System.out.println("-----------------");
                    return produto;
                }
            }
            System.out.println("Produto com ID " + id + " não encontrado");
        } catch (Exception e ) {
            System.out.println("Erro ao buscar produto por ID" + e.getMessage());
        }
        return null;
    }

    public void listarProdutosPorCategoria(TipoCategoria categoria) {
        try {
            for (Produto produto : produtos) {
                if (produto.getCategoriaT().equals(categoria)) {
                    produto.imprimir();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos por categoria");
            e.printStackTrace();
        }
    }
        public void atualizarProduto(int id, List<Produto> produtos) {
        try {
            for (Produto produto : produtos) {
                if (produto.getId_empresa() == id) {
                    System.out.println("Produto encontrado, atualize as informações: " + produto.getId_Produto());
                    produto.setNome(produto.getNome());
                    produto.setDescricao(produto.getDescricao());
                    produto.setPreco(produto.getPreco());
                    produto.setQuantidade(produto.getQuantidade());
                    produto.setCategoria(produto.getCategoriaT());
                    produto.setTaxa(produto.getTaxa());
                    produto.setUnidadeMedida(produto.getUnidadeMedida());
                    System.out.println("Produto atualizado com sucesso!");
                    return;
                }
            }
            System.out.println("Produto não pode ser atualizado");
        } catch (Exception e ) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void deletarProduto(int id) {
            try {
                Produto produtoRemover = null;
                for (Produto produto : produtos) {
                    if (produto.getId_Produto() == id) {
                        produtoRemover = produto;
                        break;
                    }
                }
                if (produtoRemover != null) {
                    produtos.remove(produtoRemover);
                    System.out.println("Produto removido com sucesso!");
                } else {
                    System.out.println("Produto não pode ser encontrado em nosso Sistema");
                }
            } catch (Exception e) {
                System.out.println("Erro ao deletar produto" + e.getMessage());
            }
        }

    public void listarProdutosLoja(int idLoja) {
            try {
                for (Produto produto : produtos) {
                    if (idLoja == produto.getId_empresa()) {
                        System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " quantidade: " + produto.getQuantidade());
                        System.out.println("Descrição: " + produto.getDescricao());
                        System.out.println();
            }
       }
     } catch (Exception e) {
                System.out.println("Erro ao listar produtos" + e.getMessage());
                e.printStackTrace();
            }
        }
    }


