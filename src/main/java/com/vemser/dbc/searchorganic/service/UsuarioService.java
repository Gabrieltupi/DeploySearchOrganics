package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public Usuario criarUsuario(Usuario usuario) throws Exception {
        try {
            Usuario novoUsuario = usuarioRepository.save(usuario);

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

    public Usuario autenticar(String login, String senha) throws RegraDeNegocioException {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if(usuario == null){
            throw new RegraDeNegocioException("Usuario nao encontrado!");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new RegraDeNegocioException("Senha incorreta");
        }
        return usuario;
    }

    public List<Usuario> exibirTodos() throws Exception {
        return usuarioRepository.findAll();
    }

    public Usuario obterUsuarioPorId(Integer id) throws Exception {
        return usuarioRepository.getById(id);
    }

    public Usuario editarUsuario(int usuarioId, Usuario usuario) throws Exception {
        try {
            Usuario usuarioEntity = obterPorId(usuarioId);

            usuarioEntity.setLogin(usuario.getLogin());
            usuarioEntity.setEmail(usuario.getEmail());
            usuarioEntity.setCpf(usuario.getCpf());
            usuarioEntity.setSenha(usuario.getSenha());
            usuarioEntity.setDataNascimento(usuario.getDataNascimento());
            usuarioEntity.setNome(usuario.getNome());
            usuarioEntity.setSobrenome(usuario.getSobrenome());



            usuarioRepository.save(usuarioEntity);

            usuario.setIdUsuario(usuarioId);

            Map<String, Object> dadosEmail = new HashMap<>();
            dadosEmail.put("nomeUsuario", usuarioEntity.getNome());
            dadosEmail.put("mensagem", "Suas informações foram atualizadas com sucesso");
            dadosEmail.put("email", usuarioEntity.getEmail());

            emailService.sendEmail(dadosEmail, "Informações Atualizadas", usuarioEntity.getEmail());

            return usuarioEntity;
        } catch (Exception e) {
            throw new Exception("Erro ao editar o usuário: " + e.getMessage(), e);
        }
    }

    public void removerUsuario(int usuarioId) throws Exception {
        try {

                usuarioRepository.deleteById(usuarioId);
                Usuario usuarioRemovido = usuarioRepository.getById(usuarioId);

                Map<String, Object> dadosEmail = new HashMap<>();
                dadosEmail.put("nomeUsuario", usuarioRemovido.getNome());
                dadosEmail.put("mensagem", "Atencao! Seu usuário foi removido do nosso serviço");
                dadosEmail.put("email", usuarioRemovido.getEmail());

                emailService.sendEmail(dadosEmail, "Usuário Removido", usuarioRemovido.getEmail());



        } catch (Exception e) {
            throw new Exception("Erro ao remover o usuário: " + e.getMessage(), e);
        }
    }

    public Usuario obterPorId(Integer id) throws Exception {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado: " + id));
    }

    public UsuarioDTO findByEmail(String email) throws RegraDeNegocioException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado: " + email));
        return new UsuarioDTO(usuario);
    }

    public UsuarioDTO findByCpf(String cpf) throws RegraDeNegocioException {
        Usuario usuario = usuarioRepository.findByCpf(cpf)
            .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado: " + cpf));
        return new UsuarioDTO(usuario);
    }
}


