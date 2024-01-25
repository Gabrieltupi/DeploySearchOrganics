package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CupomService {
    private final CupomRepository repository;

    public void adicionarCupom(Integer idEmpresa,Cupom cupom) throws Exception {

            cupom.setIdEmpresa(idEmpresa);
            repository.adicionar(cupom);

    }

    public List<Cupom> listarCupons() {
        List<Cupom> cupons = new ArrayList<>();
        try {
            for (Cupom cupom : repository.listar()) {
                cupom.toString();
                cupons.add(cupom);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cupons: " + e.getMessage());
        }
        return cupons;
    }


//    public void imprimirCuponsDisponiveis() {
//        try {
//            for (Cupom cupom : repository.listar()) {
//                if (cupom.isAtivo().equals("S")) {
//                    System.out.println("Nome: " + cupom.getNomeCupom() + " Valor do cupom: " +
//                            cupom.getTaxaDeDesconto() + " Status: " + cupom.isAtivo());
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Erro ao imprimir cupons disponíveis: " + e.getMessage());
//        }
//    }

    public Cupom buscarCupomPorId(int id) {
        try {
            for (Cupom cupom : repository.listar()) {
                System.out.println("Verificando Cupom por Id:" + cupom.getCupomId());
                if (cupom.getCupomId() == id) {
                    System.out.println("Cupom encontrado:" + cupom.getCupomId());
                    cupom.toString();
                    return cupom;
                }
            }
            System.out.println("Cupom com ID " + id + " não encontrado");
        } catch (Exception e) {
            System.out.println("Erro ao buscar cupom por ID: " + e.getMessage());
        }
        return null;
    }

    public void atualizarCupom(int id, Cupom cupom) {
        try {
            if (cupom.getCupomId() == id) {
                System.out.println("Cupom encontrado, atualize as informações: " + cupom.getCupomId());
                repository.editar(id, cupom);
                System.out.println("Cupom atualizado com sucesso!");
                return;
            }
            System.out.println("Cupom não pode ser atualizado");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cupom: " + e.getMessage());
        }
    }

    public void removerCupom(int id) {
        try {
            repository.remover(id);
            System.out.println("Cupom removido com sucesso");
        } catch (Exception e) {
            System.out.println("Erro ao deletar cupom: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Cupom> listarCupomPorEmpresa(int idEmpresa) throws BancoDeDadosException {
        return repository.listarCupomPorEmpresa(idEmpresa);
    }

    public Cupom getCupomByNameAndEmpresa(String nomeCupom, int idEmpresa) {
        try {
            return repository.getCupomByNameAndEmpresa(nomeCupom, idEmpresa);
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao buscar cupom por nome e empresa: " + e.getMessage());
            return null;
        }
    }


}







