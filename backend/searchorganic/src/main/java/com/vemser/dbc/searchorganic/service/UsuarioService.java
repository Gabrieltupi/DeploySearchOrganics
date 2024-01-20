package com.vemser.dbc.searchorganic.service;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) throws Exception {
            return usuarioRepository.adicionar(usuario);
    }

    public Usuario autenticar(String login, String senha) throws Exception {
            Usuario usuario = usuarioRepository.buscaPorLogin(login);
            if(!(usuario.getSenha().equals(senha))){
                throw new RegraDeNegocioException("Senha incorreta");
            }
            return usuario;
    }

    public List<Usuario> exibirTodos() throws Exception {
         return usuarioRepository.listar();
    }

    public Usuario obterUsuarioPorId(Integer id) throws Exception {
        return this.usuarioRepository.buscaPorId(id);
    }

    public Usuario editarUsuario(int usuarioId, Usuario usuarioEditado) throws Exception {
            if(usuarioRepository.editar(usuarioId, usuarioEditado)){
                usuarioEditado.setIdUsuario(usuarioId);
                return usuarioEditado;
            }
            throw new RegraDeNegocioException("Usuario não encontrado");
    }

    public void removerUsuario(int usuarioId) throws Exception {
           if(usuarioRepository.remover(usuarioId)){
               return;
           };
        throw new RegraDeNegocioException("Usuario não encontrado");

    }
}

