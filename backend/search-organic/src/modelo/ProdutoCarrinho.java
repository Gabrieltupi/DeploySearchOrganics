package modelo;

import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.math.BigDecimal;

public class ProdutoCarrinho extends  Produto{
    private BigDecimal quantidadePedida= new BigDecimal(0);
    public ProdutoCarrinho(Produto produto, BigDecimal quantidadePedida){
        super(produto);
        this.quantidadePedida = quantidadePedida;
    }

    public BigDecimal getQuantidadePedida() {
        return quantidadePedida;
    }

    public void setQuantidadePedida(BigDecimal quantidadePedida) {
        this.quantidadePedida = quantidadePedida;
    }
}
