package service;

import exceptions.BancoDeDadosException;
import model.Pedido;
import repository.PedidoRepository;
import utils.StatusPedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private PedidoRepository pedidoRepository = new PedidoRepository();

    public boolean adicionar(Pedido pedido) {
        try {
            if (pedido != null) {
                pedidoRepository.adicionar(pedido);
                return true;
            }
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao adicionar pedido" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<Pedido> listar() {
        try {
            for (Pedido pedido : pedidoRepository.listar()) {
                    pedido.imprimir();
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter endereços: " + e.getMessage());
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public List<Pedido> listarPorId(int id) {
        try {
            for (Pedido pedido : pedidoRepository.listar()) {
                if(id == pedido.getUsuarioId()) {
                    pedido.imprimir();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter endereços: " + e.getMessage());
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public void editarStatusPedido(int id, StatusPedido statusPedido){
        try {
            pedidoRepository.editarStatusPedido(id, statusPedido);
        } catch (Exception e) {
            System.out.println("Erro ao obter endereços: " + e.getMessage());
        }
    }

    public void excluir(int idPedido) {
        try {
            if (pedidoRepository.remover(idPedido)) {
                System.out.println("Endereço do ID " + idPedido + " foi excluído!!");
                return;
            }
            throw new IllegalArgumentException("ID não encontrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir endereço: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao excluir endereço: " + e.getMessage());
        }
    }
}
