package modelo;

import exceptions.BancoDeDadosException;
import repository.ProdutoRepository;
import utils.FormaPagamento;
import utils.validadores.TipoEntrega;
import utils.validadores.ValidadorCEP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Carrinho {
    private int idEmpresa;
    private ArrayList<ProdutoCarrinho> produtos = new ArrayList<ProdutoCarrinho>();
    private BigDecimal valorTotal = new BigDecimal(0);
    private Usuario usuario;
    private Pedido pedido;
    private ProdutoRepository produtoRepository;
    private BigDecimal frete = new BigDecimal(0);

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
        for (ProdutoCarrinho produtoCarrinho : produtos) {
            if (id == produtoCarrinho.getProduto().getIdProduto()) {
                BigDecimal quantidadeAntiga = produtoCarrinho.getQuantidadePedida();
                BigDecimal quantidadeEmEstoque = produtoCarrinho.getProduto().getQuantidade();
                BigDecimal quantidadeAtualizadaEmEstoque = quantidadeEmEstoque.subtract(novaQuantidade.subtract(quantidadeAntiga));
                if (quantidadeAtualizadaEmEstoque.compareTo(BigDecimal.ZERO) < 0) {
                    System.err.println("Quantidade indisponível no estoque");
                    return false;
                }
                produtoCarrinho.getProduto().setQuantidade(quantidadeAtualizadaEmEstoque);;
                produtoCarrinho.setQuantidadePedida(novaQuantidade);
                atualizarValorTotal();
                return true;

            }
        }

        System.out.println("Produto não encontrado!");
        return false;
    }

    public boolean removerProdutoDoCarrinho(int id) {
        for(ProdutoCarrinho produtoCarrinho : produtos){
            if (produtoCarrinho.getProduto().getIdProduto() == id) {
                produtos.remove(produtoCarrinho);
                atualizarValorTotal();
                return true;
            }
        }

        System.out.println("ID não encontrado!!");
        return false;

    }

    public void listarProdutosDoCarrinho() throws BancoDeDadosException {
        for(ProdutoCarrinho produtoCarrinho : produtos){
            System.out.println("Número: " + produtoCarrinho.getProduto().getIdProduto() + " Nome do produto: " + produtoCarrinho.getProduto().getNome()
                    + " Quantidade: " + produtoCarrinho.getQuantidadePedida());
        }

        System.out.println("Valor final (Sem frete): " + valorTotal);
    }

    public void limparSacola() {
            for (ProdutoCarrinho produtoCarrinho : produtos) {
                produtoCarrinho.getProduto().setQuantidade(produtoCarrinho.getQuantidadePedida().add(produtoCarrinho.getProduto().getQuantidade()));
            }
        produtos = new ArrayList<ProdutoCarrinho>();
        atualizarValorTotal();
    }

    public void finalizarPedido(FormaPagamento formaPagamento, LocalDate dataDeEntrega,
                                Endereco endereco, Cupom cupom, TipoEntrega tipoEntrega){

        if(ValidadorCEP.isCepValido(endereco.getCep()) != null){
            pedido = new Pedido(usuario.getIdUsuario(), produtos,
                    formaPagamento, dataDeEntrega, endereco, tipoEntrega, cupom, valorTotal, frete);
            pedido.imprimir();
        } else {
            System.out.println("CEP invalido - pedido não foi finalizado");
        }
    }

    public void atualizarValorTotal() {
        valorTotal = BigDecimal.ZERO;
        for (ProdutoCarrinho produtoCarrinho: produtos) {
            BigDecimal quantidade = produtoCarrinho.getQuantidadePedida();
            BigDecimal precoProduto = produtoCarrinho.getProduto().getPreco();
            valorTotal = valorTotal.add(precoProduto.multiply(quantidade));
        }
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }
}