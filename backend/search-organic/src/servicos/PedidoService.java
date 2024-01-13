package servicos;

import modelo.Pedido;
import repository.PedidoRepository;

import java.util.ArrayList;

public class PedidoService {
    private ArrayList<Pedido> pedidos = new java.util.ArrayList<>();

    public void criarPedido(){}

    private PedidoRepository pedidoRepository = new PedidoRepository();

    public void adicionarPedido(Pedido pedido) {
        try {
            pedidos.add(pedido);
            System.out.println("Pedido adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar pedido" + e.getMessage());
            e.printStackTrace();
        }
    }
        public void criarPedido (Pedido pedido) {
            try {
                this.pedidos.add(pedido);
                System.out.println("Pedido criado com sucesso!");
            } catch (Exception e) {
                System.out.println("ERRO ao criar pedido: " + e.getMessage());
                e.printStackTrace();
            }
        }

            public void listarPedidos () {
                try {
                        for (Pedido pedido : pedidoRepository.listar()) {

                        }
                } catch (Exception e) {
                    System.out.println("ERRO ao listar pedidos: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            public void atualizarPedido ( int idPedido, Pedido pedidoAtualizado){
                try {
                    if (idPedido >= 0 && idPedido < this.pedidos.size()) {
                        Pedido pedido = this.pedidos.get(idPedido);
                        pedido.setEntregue(pedidoAtualizado.getEntregue());
                        pedido.setProdutos(pedidoAtualizado.getProdutos());
                        pedido.setTotal(pedidoAtualizado.getTotal());
                        pedido.setFormaPagamento(pedidoAtualizado.getFormaPagamento());
                        System.out.println("Pedido atualizado com sucesso!");
                    } else {
                        System.out.println("ID de pedido inválido");
                    }
                } catch (Exception e) {
                    System.out.println("ERRO ao atualizar pedido: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            public void excluirPedido ( int idPedido){
                try {
                    if (idPedido >= 0 && idPedido < this.pedidos.size()) {
                        this.pedidos.remove(idPedido);
                        System.out.println("Pedido removido com sucesso!");
                    } else {
                        System.out.println("ID de pedido inválido");
                    }
                } catch (Exception e) {
                    System.out.println("ERRO ao excluir pedido: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
