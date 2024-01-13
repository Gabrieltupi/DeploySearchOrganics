package modelo;

import exceptions.BancoDeDadosException;
import repository.ProdutoRepository;
import utils.FormaPagamento;
import utils.validadores.TipoEntrega;
import utils.validadores.ValidadorCEP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private int idEmpresa;
    private ArrayList<ProdutoCarrinho> produtos = new ArrayList<ProdutoCarrinho>();
    private BigDecimal valorTotal = new BigDecimal(0);
    private Usuario usuario;
    private Pedido pedido;
    private ProdutoRepository produtoRepository;

    public Carrinho(Usuario usuario, int idEmpresa) {
        this.idEmpresa = idEmpresa;
        this.usuario = usuario;
    }

    public Carrinho(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa){
        this.idEmpresa = idEmpresa;
    }
    public ArrayList<ProdutoCarrinho> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<ProdutoCarrinho>  produtos) {
        this.produtos = produtos;
    }


    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean adicionarProdutoAoCarrinho(Produto produto, BigDecimal quantidade) {
        if (quantidade.compareTo(BigDecimal.ZERO) < 0) {
            System.err.println("Quantidade inválida");
            return false;
        }
        ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho(produto, quantidade);
        this.produtos.add(produtoCarrinho);
        atualizarValorTotal();
        produto.setQuantidade(produto.getQuantidade().subtract(quantidade));
        return true;
    }

    public boolean editarQuantidadeProdutoDaSacola(int id, BigDecimal novaQuantidade) {
        for (ProdutoCarrinho produto : produtos) {
            if (id == produto.getId_Produto()) {
                BigDecimal quantidadeAntiga = produto.getQuantidadePedida();
                BigDecimal quantidadeEmEstoque = produto.getQuantidade();
                BigDecimal quantidadeAtualizadaEmEstoque = quantidadeEmEstoque.subtract(novaQuantidade.subtract(quantidadeAntiga));
                if (quantidadeAtualizadaEmEstoque.compareTo(BigDecimal.ZERO) < 0) {
                    System.err.println("Quantidade indisponível no estoque");
                    return false;
                }
                produto.setQuantidade(quantidadeAtualizadaEmEstoque);;
                produto.setQuantidadePedida(novaQuantidade);
                atualizarValorTotal();
                return true;

            }
        }

        System.out.println("Produto não encontrado!");
        return false;
    }

    public boolean removerProdutoDoCarrinho(int id) {
        for(ProdutoCarrinho produto : produtos){
            if (produto.getId_Produto() == id) {
                produtos.remove(produto);
                atualizarValorTotal();
                return true;
            }
        }

        System.out.println("ID não encontrado!!");
        return false;

    }

    public void listarProdutosDoCarrinho() throws BancoDeDadosException {
        for(ProdutoCarrinho produto : produtos){
            System.out.println("Número: " + produto.getId_Produto() + " Nome do produto: " + produto.getNome()
                    + " Quantidade: " + produto.getQuantidadePedida());
        }

        System.out.println("Valor final (Sem frete): " + valorTotal);
    }

    public void limparSacola() {
            for (ProdutoCarrinho produto : produtos) {
                produto.setQuantidade(produto.getQuantidadePedida().add(produto.getQuantidade()));
            }
        produtos = new ArrayList<ProdutoCarrinho>();
        atualizarValorTotal();
    }

    public void finalizarPedido(FormaPagamento formaPagamento, LocalDate dataDeEntrega,
                                Endereco endereco, Cupom cupom, TipoEntrega tipoEntrega){

        if(ValidadorCEP.isCepValido(endereco.getCep()) != null){
            pedido = new Pedido(usuario.getIdUsuario(), produtos,
                    formaPagamento, dataDeEntrega, endereco, tipoEntrega, cupom, valorTotal);
            pedido.imprimir();
        } else {
            System.out.println("CEP invalido - pedido não foi finalizado");
        }
    }

    public void atualizarValorTotal() {
        valorTotal = BigDecimal.ZERO;
        for (ProdutoCarrinho produto: produtos) {
            BigDecimal quantidade = produto.getQuantidadePedida();
            BigDecimal precoProduto = produto.getPreco();
            valorTotal = valorTotal.add(precoProduto.multiply(quantidade));
        }
    }
}