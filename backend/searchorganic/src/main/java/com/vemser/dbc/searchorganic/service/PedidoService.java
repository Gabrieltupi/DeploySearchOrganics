package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.repository.PedidoRepository;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
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
            pedidoRepository.remover(idPedido);
            System.out.println("Pedido do ID " + idPedido + " foi excluído!!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir pedido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao excluir pedido: " + e.getMessage());
        }
    }

}
