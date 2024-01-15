package model;

import exceptions.BancoDeDadosException;
import repository.ProdutoRepository;
import service.PedidoService;
import service.ProdutoService;
import utils.FormaPagamento;
import utils.StatusPedido;
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
    private BigDecimal frete = new BigDecimal(0);
    private    ProdutoService produtoService = new ProdutoService();
    private PedidoService pedidoService = new PedidoService();

    public Carrinho(Usuario usuario, int idEmpresa) {
        this.idEmpresa = idEmpresa;
        this.usuario = usuario;
    }
    public Carrinho(){}

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
        if (quantidade.compareTo(BigDecimal.ZERO) < 0 || produto.getQuantidade().compareTo(quantidade) < 0) {
            System.err.println("Quantidade inválida");
            return false;
        }
        ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho(produto, quantidade);
        this.produtos.add(produtoCarrinho);
        atualizarValorTotal();
        return true;
    }

    public boolean editarQuantidadeProdutoDaSacola(int id, BigDecimal novaQuantidade) {
        for (ProdutoCarrinho produtoCarrinho : produtos) {
            Produto produto = produtoCarrinho.getProduto();
            if (id == produto.getIdProduto()) {
                if (produto.getQuantidade().subtract(novaQuantidade).compareTo(BigDecimal.ZERO) < 0) {
                    System.err.println("Quantidade indisponível no estoque");
                    return false;
                }

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
            Produto produto = produtoCarrinho.getProduto();
            if (produto.getIdProduto() == id) {
                produtos.remove(produtoCarrinho);
                atualizarValorTotal();
                return true;
            }
        }

        System.out.println("ID não encontrado!!");
        return false;

    }

    public void listarProdutosDoCarrinho()   {
        for(ProdutoCarrinho produtoCarrinho : produtos){
            System.out.println("Número: " + produtoCarrinho.getProduto().getIdProduto() + " Nome do produto: " + produtoCarrinho.getProduto().getNome()
                    + " Quantidade: " + produtoCarrinho.getQuantidadePedida());
        }

        System.out.println("Valor final (Sem frete): " + valorTotal);
    }

    public void limparSacola() {
        produtos = new ArrayList<ProdutoCarrinho>();
        atualizarValorTotal();
    }

    public BigDecimal getValorCarrinho() {
        BigDecimal valorCarrinho = new BigDecimal(0);
        for (ProdutoCarrinho produtoCarrinho: produtos) {
            valorCarrinho = valorCarrinho.add(produtoCarrinho.getProduto().getPreco().multiply(produtoCarrinho.getQuantidadePedida()));
        }
        return valorCarrinho;
    }

    public Boolean finalizarPedido(FormaPagamento formaPagamento, LocalDate dataDeEntrega,
                                Endereco endereco, Cupom cupom){

        if(ValidadorCEP.isCepValido(endereco.getCep()) != null){
            pedido = new Pedido(usuario.getIdUsuario(), produtos,
                    formaPagamento, dataDeEntrega, endereco, cupom, valorTotal, frete, StatusPedido.AGUARDANDO_PAGAMENTO, getValorCarrinho());
            pedidoService.adicionar(pedido);
            pedido.imprimir();
            for (ProdutoCarrinho produtoCarrinho : produtos) {
                Produto produto = produtoCarrinho.getProduto();
                BigDecimal quantidadePedida = produtoCarrinho.getQuantidadePedida();

                produto.setQuantidade(produto.getQuantidade().subtract(quantidadePedida));
                produtoService.atualizarSilenciosamente(produto);
            }
            return true;
        } else {
            System.out.println("CEP invalido");
            return false;
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