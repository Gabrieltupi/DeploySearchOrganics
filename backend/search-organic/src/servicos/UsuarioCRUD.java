package servicos;
import java.util.Date;
import modelo.Usuario;
import modelo.Endereco;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCRUD {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static int proximoId = 1;

    public void criarUsuario(String login, String password, String nome, String sobrenome, Endereco endereco, Date dataNascimento) {
        Usuario novoUsuario = new Usuario(login, password, nome, sobrenome, endereco, dataNascimento);
        novoUsuario.setUsuarioId(proximoId++);
        usuarios.add(novoUsuario);
    }

    public Usuario buscarUsuarioPorId(int usuarioId) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuarioId() == usuarioId) {
                return usuario;
            }
        }
        return null; // Retorna null se o usuário não for encontrado
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