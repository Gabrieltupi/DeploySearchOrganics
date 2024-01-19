package com.vemser.dbc.searchorganic.service;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.SenhaIncorretaException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void criarUsuario(Usuario usuario) throws BancoDeDadosException {
        try {
            usuarioRepository.adicionar(usuario);
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
            e.printStackTrace();
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

    public void exibirUsuario(int id) {
        try {
            List<Usuario> usuarios = usuarioRepository.listar();
            for(Usuario usuario: usuarios){
                if(usuario.getIdUsuario() == id){
                    usuario.imprimir();
                }
            }
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao exibir usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editarUsuario(int usuarioId, Usuario usuarioEditado) {
        try {
            boolean usuarioEditadoComSucesso = usuarioRepository.editar(usuarioId, usuarioEditado);
            if (usuarioEditadoComSucesso) {
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
            } else {
                System.out.println("Usuário não encontrado");
            }
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

