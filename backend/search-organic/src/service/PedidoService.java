package service;

import exceptions.BancoDeDadosException;
import model.Pedido;
import repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private PedidoRepository pedidoRepository = new PedidoRepository();

    public void adicionar(Pedido pedido) {
        try {
            if (pedido != null) {
                pedidoRepository.adicionar(pedido);
            }
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao adicionar pedido" + e.getMessage());
            e.printStackTrace();
        }
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
