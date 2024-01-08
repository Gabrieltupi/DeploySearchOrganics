package servicos;

import exceptions.UsuarioJaCadastradoException;
import modelo.Usuario;
import modelo.Endereco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCRUD {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static int proximoId = 1;

    public void criarUsuario(String login, String password, String nome, String sobrenome, Endereco endereco, LocalDate dataNascimento) throws UsuarioJaCadastradoException {
        if (verificarUsuarioCadastrado(login)) {
            throw new UsuarioJaCadastradoException();
        }
        criarUsuario(new Usuario(login, password, nome, sobrenome, endereco, dataNascimento));
    }

    public void criarUsuario(Usuario usuario) throws UsuarioJaCadastradoException {
        if (verificarUsuarioCadastrado(usuario.getLogin())) {
            throw new UsuarioJaCadastradoException();
        }
        usuario.setUsuarioId(proximoId++);
        usuarios.add(usuario);
        System.out.println("-----------------");
    }

    private boolean verificarUsuarioCadastrado(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equalsIgnoreCase(login)) {
                return true;
            }
        }
        return false;
    }

    public Usuario buscarUsuarioPorLoginESenha(String login, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login) && usuario.getPassword().equals(senha)) {
                System.out.println("-----------------");
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorId(int usuarioId) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuarioId() == usuarioId) {
                return usuario;
            }
        }
        return null;
    }

    public void exibirTodos() {
        for (Usuario usuario : usuarios) {
            usuario.imprimir();
            System.out.println("-----------------");
        }
    }

    public void editarUsuario(int usuarioId, Usuario usuarioEditado) {
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
    }

    public void removerUsuario(int usuarioId) {
        usuarios.removeIf(usuario -> usuario.getUsuarioId() == usuarioId);
    }
}
