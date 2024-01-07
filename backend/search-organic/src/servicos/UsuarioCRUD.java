package servicos;
import java.time.LocalDate;
import java.util.Date;
import modelo.Usuario;
import modelo.Endereco;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCRUD {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static int proximoId = 1;

    public void criarUsuario(String login, String password, String nome, String sobrenome, Endereco endereco, LocalDate dataNascimento) {
        criarUsuario(new Usuario(login, password, nome, sobrenome, endereco, dataNascimento));
    }

    public void criarUsuario(Usuario usuario) {
        usuario.setUsuarioId(proximoId++);
        usuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorLoginESenha(String login, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login) && usuario.getPassword().equals(senha)) {
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

    public void exibirTodos(){
        for(Usuario usuario : usuarios){
            usuario.imprimir();
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