package com.vemser.dbc.searchorganic.service;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository,EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    public Usuario criarUsuario(Usuario usuario) throws Exception {
        try {
            Usuario novoUsuario = usuarioRepository.adicionar(usuario);

            Map<String, Object> dadosEmail = new HashMap<>();
            dadosEmail.put("nomeUsuario", novoUsuario.getNome());
            dadosEmail.put("mensagem", "Bem-vindo ao nosso serviço");
            dadosEmail.put("email", novoUsuario.getEmail());

            emailService.sendEmail(dadosEmail, "Bem-vindo ao nosso serviço", novoUsuario.getEmail());

            return novoUsuario;
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Erro ao criar o usuário devido a violação de integridade: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Erro ao criar o usuário: " + e.getMessage(), e);
        }
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
        try {
            if (usuarioRepository.editar(usuarioId, usuarioEditado)) {
                usuarioEditado.setIdUsuario(usuarioId);

                Map<String, Object> dadosEmail = new HashMap<>();
                dadosEmail.put("nomeUsuario", usuarioEditado.getNome());
                dadosEmail.put("mensagem", "Suas informações foram atualizadas com sucesso");
                dadosEmail.put("email", usuarioEditado.getEmail());

                emailService.sendEmail(dadosEmail, "Informações Atualizadas", usuarioEditado.getEmail());

                return usuarioEditado;
            }
            throw new RegraDeNegocioException("Usuário não encontrado");
        } catch (Exception e) {
            throw new Exception("Erro ao editar o usuário: " + e.getMessage(), e);
        }
    }

    public void removerUsuario(int usuarioId) throws Exception {
        try {
            if (usuarioRepository.remover(usuarioId)) {
                Usuario usuarioRemovido = usuarioRepository.buscaPorId(usuarioId);

                Map<String, Object> dadosEmail = new HashMap<>();
                dadosEmail.put("nomeUsuario", usuarioRemovido.getNome());
                dadosEmail.put("mensagem", "Atencao! Seu usuário foi removido do nosso serviço");
                dadosEmail.put("email", usuarioRemovido.getEmail());

                emailService.sendEmail(dadosEmail, "Usuário Removido", usuarioRemovido.getEmail());

                return;
            }
            throw new RegraDeNegocioException("Usuário não encontrado");
        } catch (Exception e) {
            throw new Exception("Erro ao remover o usuário: " + e.getMessage(), e);
        }
    }

}


