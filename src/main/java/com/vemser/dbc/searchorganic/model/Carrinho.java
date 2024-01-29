package com.vemser.dbc.searchorganic.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.ArrayList;

@Schema(hidden = true)
public class Carrinho {
    private int idEmpresa;
    private ArrayList<ProdutoCarrinho> produtos = new ArrayList<ProdutoCarrinho>();
    private BigDecimal valorTotal = new BigDecimal(0);
    private Usuario usuario;
    private Pedido pedido;
    private BigDecimal frete = new BigDecimal(0);

    public Carrinho(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public ArrayList<ProdutoCarrinho> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<ProdutoCarrinho> produtos) {
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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }
}
