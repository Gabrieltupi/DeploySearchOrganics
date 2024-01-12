package servicos;

import exceptions.UsuarioJaCadastradoException;
import modelo.Usuario;
import modelo.Endereco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static int proximoId = 1;

    public void criarUsuario(Usuario usuario) throws UsuarioJaCadastradoException {
        try {
            if (verificarUsuarioCadastrado(usuario.getLogin())) {
                throw new UsuarioJaCadastradoException();
            }
            criarUsuario(new Usuario(usuario.getNome(), usuario.getSobrenome(),usuario.getEndereco(),usuario.getCpf(),
                    usuario.getDataNascimento(), usuario.getEmail(), usuario.getLogin(), usuario.getPassword()));
            usuario.setUsuarioId(proximoId++);
            usuarios.add(usuario);
            System.out.println("-----------------");

        } catch (UsuarioJaCadastradoException e) {
            System.out.println("Usuário já cadastrado" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private boolean verificarUsuarioCadastrado(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equalsIgnoreCase(login)) {
                return true;
            }
        }
        return false;
    }

    public Usuario buscarUsuarioPorId(int usuarioId) {
        try {
            for (Usuario usuario : usuarios) {
                if (usuario.getUsuarioId() == usuarioId) {
                    return usuario;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void exibirTodos() {
        try {
            for (Usuario usuario : usuarios) {
                usuario.imprimir();
                System.out.println("-----------------");
            }
        } catch (Exception e){
            System.out.println("Erro ao exibir usuários" + e.getMessage());
        }
}

    public void editarUsuario(int usuarioId, Usuario usuarioEditado) {
        try {
            for (Usuario usuario : usuarios) {
                if (usuario.getUsuarioId() == usuarioId) {
                    usuario.setLogin(usuarioEditado.getLogin());
                    usuario.setPassword(usuarioEditado.getPassword());
                    usuario.setNome(usuarioEditado.getNome());
                    usuario.setSobrenome(usuarioEditado.getSobrenome());
                    usuario.setEndereco(usuarioEditado.getEndereco());
                    usuario.setDataNascimento(usuarioEditado.getDataNascimento());
                    System.out.println("Edição realizada com sucesso!");
                    return;
                }
            }
            System.out.println("Usuário não encontrado");
        } catch (Exception e) {
            System.out.println(" Erro ao editar usuário" + e.getMessage());
        }
    }
    public void removerUsuario(int usuarioId) {
        try{
        usuarios.removeIf(usuario -> usuario.getUsuarioId() == usuarioId);
        } catch (Exception e) {
            System.out.println("Erro ao remover usuário" + e.getMessage());
        }
    }
}
