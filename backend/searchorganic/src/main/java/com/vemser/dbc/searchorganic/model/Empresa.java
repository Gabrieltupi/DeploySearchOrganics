package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.interfaces.IImpressao;
import lombok.*;

import java.util.ArrayList;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Empresa implements IImpressao {
    private Integer idEmpresa;
    private Integer idUsuario;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;
    private ArrayList<Produto> produtos = new ArrayList<>();

    public Empresa(String nomeFantasia, String cnpj, String razaoSocial, String inscricaoEstadual, String setor, Integer idUsuario) {
    }


    @Override
    public void imprimir() {
        System.out.println("ID do da Empresa: " + getIdEmpresa());
        System.out.println("\nDados da Empresa: \n");
        System.out.println("Nome da empresa: " + getNomeFantasia());
        System.out.println("CNPJ da empresa: " + getCnpj());
        System.out.println("Razao Social da empresa: " + getRazaoSocial());
        System.out.println("Incriçao Social da empresa: " + getInscricaoEstadual());
        System.out.println("Setor da empresa: \n" + getSetor());

        if (produtos.isEmpty()) {
            System.out.println("A empresa não possui produtos.");
        } else {
            System.out.println("Lista de Produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }
}
