package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.interfaces.ICupomServicos;
import com.vemser.dbc.searchorganic.interfaces.IImpressao;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
public class Cupom implements IImpressao, ICupomServicos {
    private Integer cupomId;
    @NotEmpty(message = "O nome nao pode ser vazio")
    @Size(min = 2, max = 30,message = "O nome conter entre 2 a 30 caracteres!")
    private String nomeCupom;
    @NotNull
    private TipoAtivo ativo;
    @NotEmpty(message = "A descricao nao pode ser vazia")
    @Size(min = 2, max = 100,message = "A descricao conter entre 2 a 100 caracteres!")
    private String descricao;
    @NotEmpty(message = "A taxa de desconto nao pode ser vazia")

    private BigDecimal taxaDeDesconto = new BigDecimal(0);
    private Integer idEmpresa;


    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public void imprimir() {
        System.out.println("\nInformações sobre o cupom de desconto:");
        System.out.println("ID do cupom: " + getCupomId());
        System.out.println("Produto associado: " + getNomeCupom());
        System.out.println("Descrição do cupom: " + getDescricao());
        System.out.println("Taxa de desconto: " + this.taxaDeDesconto);
    }

    @Override
    public boolean eValido() {
        if (this.ativo == TipoAtivo.S) {
            System.out.println("O cupom é válido.");
            return true;
        } else {
            System.out.println("O cupom é inválido.");
            return false;
        }

    }

    @Override
    public void ativarCupom() {
        if (this.ativo.getStatus().equals("S")) {
            System.out.println("O cupom já está ativo!");
        } else {
            this.ativo = TipoAtivo.S;
            System.out.println("O cupom foi ativado!");
        }
    }

    @Override
    public boolean desativarCupom() {
        if (this.ativo.getStatus().equals("S")) {
            this.ativo = TipoAtivo.N;
            System.out.println("O cupom agora está inativo.");
            return true;
        } else {
            System.out.println("O cupom já está inativo.");
            return false;
        }
    }

    public String isAtivo() {
        return ativo.getStatus();
    }

    public void setAtivo(String ativo) {
        this.ativo = TipoAtivo.fromString(ativo);
    }

}

