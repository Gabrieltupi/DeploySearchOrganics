package servicos;

import exceptions.BancoDeDadosException;
import exceptions.SenhaIncorretaException;
import exceptions.UsuarioJaCadastradoException;
import modelo.Usuario;
import repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void criarUsuario(Usuario usuario) throws BancoDeDadosException{
        try {
            usuarioRepository.adicionar(usuario);
            System.out.println("Usuário criado com sucesso!");
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Usuario autenticar(String login, String senha){
        try{
            Usuario usuario = usuarioRepository.buscaPorLogin(login);
            if(!(usuario.getSenha().equals(senha))){
                throw new SenhaIncorretaException();
            }

            return usuario;
        } catch (SenhaIncorretaException senhaIncorrExce) {
            throw new RuntimeException(senhaIncorrExce.getMessage());
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
    }

    public void exibirTodos() {
        try {
            List<Usuario> usuarios = usuarioRepository.listar();
            usuarios.forEach(usuario -> {
                usuario.imprimir();
                System.out.println("-----------------");
            });
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao exibir usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editarUsuario(int usuarioId, Usuario usuarioEditado) {
        try {
            boolean usuarioEditadoComSucesso = usuarioRepository.editar(usuarioId, usuarioEditado);
            if (usuarioEditadoComSucesso) {
                System.out.println("Usuário editado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado");
            }
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao editar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removerUsuario(int usuarioId) {
        try {
            boolean usuarioRemovido = usuarioRepository.remover(usuarioId);
            if (usuarioRemovido) {
                System.out.println("Usuário removido com sucesso!");
            } else {
                System.out.println("Usuário não encontrado");
            }
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

