package servicos;

import java.util.ArrayList;
import modelo.Cupom;

public class CupomServicos {

    private ArrayList<Cupom> cupons;

    public CupomServicos() {
        this.cupons = new ArrayList<>();
    }

    public void adicionarCupom(Cupom cupom) {
        try {
            cupons.add(cupom);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar cupom: " + e.getMessage());
        }
    }

    public void listarCupons() {
        try {
            for (Cupom cupom : cupons) {
                cupom.imprimir();
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cupons: " + e.getMessage());
        }
    }

    public void imprimirCuponsDisponiveis() {
        try {
            for (Cupom cupom : cupons) {
                if (cupom.isAtivo()) {
                    System.out.println("Nome: " + cupom.getNomeCupom() + " Valor do cupom: " +
                            cupom.getTaxaDeDesconto() + " Status: " + cupom.isAtivo());
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao imprimir cupons disponíveis: " + e.getMessage());
        }
    }

    public Cupom buscarCupomPorId(int id) {
        try {
            for (Cupom cupom : cupons) {
                System.out.println("Verificando Cupom por Id:" + cupom.getCupomId());
                if (cupom.getCupomId() == id) {
                    System.out.println("Cupom encontrado:" + cupom.getCupomId());
                    cupom.imprimir();
                    return cupom;
                }
            }
            System.out.println("Cupom com ID " + id + " não encontrado");
        } catch (Exception e) {
            System.out.println("Erro ao buscar cupom por ID: " + e.getMessage());
        }
        return null;
    }

    public void atualizarCupom(int id, Cupom cupoms) {
        try {
            for (Cupom cupom : cupons) {
                if (cupom.getCupomId() == id) {
                    System.out.println("Cupom encontrado, atualize as informações: " + cupom.getCupomId());
                    cupom.setNomeCupom(cupoms.getNomeCupom());
                    cupom.setDescricao(cupoms.getDescricao());
                    cupom.setTaxaDeDesconto(cupoms.getTaxaDeDesconto());
                    System.out.println("Cupom atualizado com sucesso!");
                    return;
                }
            }
            System.out.println("Cupom não pode ser atualizado");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cupom: " + e.getMessage());
        }
    }

    public void deletarCupom(int id) {
        try {
            for (Cupom cupom : cupons) {
                if (cupom.getCupomId() == id) {
                    cupons.remove(cupom);
                    System.out.println("Cupom removido com sucesso!");
                    return;
                }
            }
            System.out.println("Cupom não pode ser encontrado em nosso Sistema");
        } catch (Exception e) {
            System.out.println("Erro ao deletar cupom: " + e.getMessage());
        }
    }
}
