package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.interfaces.IImpressao;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.validadores.TipoEntrega;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Pedido  {
    private Integer idPedido;
    private Integer idUsuario;
    private BigDecimal total;
    private FormaPagamento formaPagamento;
    private StatusPedido statusPedido;
    private Boolean entregue;
    private LocalDate dataDeEntrega;
    private Endereco endereco;
    private LocalDate inicioEntrega;
    private TipoEntrega tipoEntrega;
    private ArrayList<ProdutoCarrinho> produtos;
    private Cupom cupom;
    private BigDecimal valorFrete = new BigDecimal(0);
    private BigDecimal precoCarrinho = new BigDecimal(0);

    public Pedido(Integer idUsuario, ArrayList<ProdutoCarrinho> produtos,
                  FormaPagamento formaPagamento, LocalDate dataDeEntrega,
                  Endereco endereco, Cupom cupom, BigDecimal total, BigDecimal frete,
                  StatusPedido status, BigDecimal precoCarrinho, TipoEntrega tipoEntrega) {
        this.cupom = cupom;
        this.statusPedido = status;
        this.produtos = produtos;
        this.formaPagamento = formaPagamento;
        this.dataDeEntrega = dataDeEntrega;
        this.endereco = endereco;
        this.idUsuario = idUsuario;
        this.tipoEntrega = tipoEntrega;
        if(tipoEntrega == tipoEntrega.RETIRAR_NO_LOCAL){
            this.total = total.subtract(cupom.getTaxaDeDesconto());

        }else {
            this.total = total.add(calcularFrete(endereco.getCep())).subtract(cupom.getTaxaDeDesconto());
        }
        this.total = total.subtract(cupom.getTaxaDeDesconto());
        this.entregue = false;
        this.inicioEntrega = LocalDate.now();
        this.valorFrete = frete;
        this.precoCarrinho = precoCarrinho;
    }

    public BigDecimal calcularFrete(String cep) {
        BigDecimal frete = new BigDecimal("0.00");
        String regiao = ValidadorCEP.isCepValido(cep);

        if (regiao == null) return null;

        if (regiao.equals("SP - Capital")) {
            frete = new BigDecimal("10.00");
        }
        if (regiao.equals("SP - Área Metropolitana")) {
            frete = new BigDecimal("15.00");
        }
        if (regiao.equals("SP - Litoral")) {
            frete = new BigDecimal("20.00");
        }
        if (regiao.equals("SP - Interior")) {
            frete = new BigDecimal("25.00");
        }

        return frete;
    }

}
