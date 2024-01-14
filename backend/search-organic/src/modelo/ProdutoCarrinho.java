package modelo;

import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.math.BigDecimal;

public class ProdutoCarrinho{
    private Produto produto;
    private BigDecimal quantidadePedida= new BigDecimal(0);
    public ProdutoCarrinho(Produto produto, BigDecimal quantidadePedida){
        this.produto = produto;
        this.quantidadePedida = quantidadePedida;
    }

    public ProdutoCarrinho() {

    }

    public BigDecimal getQuantidadePedida() {
        return quantidadePedida;
    }

    public void setQuantidadePedida(BigDecimal quantidadePedida) {
        this.quantidadePedida = quantidadePedida;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
