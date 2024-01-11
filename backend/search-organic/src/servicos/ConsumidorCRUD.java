//package servicos;
//
//import exceptions.UsuarioJaCadastradoException;
//import modelo.Consumidor;
//import modelo.Usuario;
//
//import java.util.ArrayList;
//
//public class ConsumidorCRUD {
//
//    private ArrayList<Consumidor> consumidores;
//    private static int proximoId = 1;
//
//
//    public ConsumidorCRUD() {
//        this.consumidores = new ArrayList<>();
//    }
//
//    public void criar(Consumidor novoConsumidor) {
//        consumidores.add(novoConsumidor);
//    }
//
//    public void criarUsuario(Consumidor consumidor) throws UsuarioJaCadastradoException {
//        if (verificarUsuarioCadastrado(consumidor.getLogin())) {
//            throw new UsuarioJaCadastradoException();
//        }
//        consumidor.setUsuarioId(proximoId++);
//        consumidores.add(consumidor);
//        System.out.println("-----------------");
//    }
//    private boolean verificarUsuarioCadastrado(String login) {
//        for (Consumidor consumidor : consumidores) {
//            if (consumidor.getLogin().equalsIgnoreCase(login)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public Consumidor buscarUsuarioPorId(int consumidorId) {
//        for (Consumidor consumidor : consumidores) {
//            if (consumidor.getUsuarioId() == consumidorId) {
//                return consumidor;
//            }
//        }
//        return null;
//    }
//
//    public void exibirTodos() {
//        for (Consumidor consumidor : consumidores) {
//            consumidor.imprimir();
//        }
//    }
//
//    public void exibirConsumidor() {
//        for (Consumidor consumidor : consumidores) {
//            System.out.println("Nome: " + consumidor);
//        }
//    }
//
//    // Opção 1.1
//    public void exibir(int id) {
//        for (Consumidor consumidor : consumidores) {
//            if (id == consumidor.getId()) {
//                consumidor.imprimir();
//                return;
//            }
//        }
//        System.out.println("Consumidor não encontrado!");
//    }
//
//    public Consumidor buscarUsuarioPorLoginESenha(String login, String senha) {
//        for (Consumidor consumidor : consumidores) {
//            if (consumidor.getLogin().equals(login) && consumidor.getPassword().equals(senha)) {
//                System.out.println("-----------------");
//                return consumidor;
//            }
//        }
//        return null;
//    }
//
//    // Opção 1.2
//    public void editar(int id, Consumidor consumidorEditado) {
//        for (Consumidor consumidor : consumidores) {
//            if (id == consumidor.getId()) {
//                consumidor.setNome(consumidorEditado.getNome());
//                consumidor.setSobrenome(consumidorEditado.getSobrenome());
//                consumidor.setEndereco(consumidorEditado.getEndereco());
//                consumidor.setDataNascimento(consumidorEditado.getDataNascimento());
//                consumidor.setCpf(consumidorEditado.getCpf());
//                return;
//            }
//        }
//        System.out.println("Consumidor não encontrado");
//    }
//
//    public void remover(int id) {
//        Consumidor consumidorRemover = null;
//        for (Consumidor consumidor : consumidores) {
//            if (consumidor.getId() == id) {
//                consumidorRemover = consumidor;
//                break;
//            }
//        }
//        if (consumidorRemover != null) {
//            consumidores.remove(consumidorRemover);
//        } else {
//            System.out.println("Consumidor não encontrado!");
//        }
//    }
//}
