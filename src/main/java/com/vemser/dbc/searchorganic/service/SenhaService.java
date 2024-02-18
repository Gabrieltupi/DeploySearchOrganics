package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.dto.senha.RecuperarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.ResetarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.SenhaDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.interfaces.ISenhaService;
import com.vemser.dbc.searchorganic.utils.SenhaUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class SenhaService implements ISenhaService {
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final SenhaUtil senhaUtil;

    public SenhaDTO recover(RecuperarSenhaDTO senhaDto) throws Exception {
        try {
            Usuario usuario = usuarioService.findByLoginAndEmail(senhaDto.getLogin(), senhaDto.getEmail());
            String senha = senhaUtil.gerarSenha(8);
            usuario.setSenha(passwordEncoder.encode(senha));

            usuarioService.salvarUsuario(usuario);

            sendRecoverEmail(usuario, senha);

            return new SenhaDTO( "Solicitação realizada com sucesso. Email de redefinição enviado!");
        } catch (Exception e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public SenhaDTO reset(ResetarSenhaDTO senhaDto) throws Exception {
        try {
            Usuario usuario = usuarioService.getLoggedUser();

            String senhaAntiga = passwordEncoder.encode(senhaDto.getSenhaAtual());
            usuarioService.findByLoginAndSenha(usuario.getLogin(), senhaAntiga)
                    .orElseThrow(() ->  new RegraDeNegocioException("Senha atual inválida"));

            usuario.setSenha(passwordEncoder.encode(senhaDto.getNovaSenha()));

            usuarioService.salvarUsuario(usuario);

            sendResetEmail(usuario);

            return new SenhaDTO( "Solicitação realizada com sucesso. A senha foi atualizada!");
        } catch (Exception e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public void sendRecoverEmail(Usuario usuario, String senha) throws Exception {
        Map<String, Object> dadosEmail = new HashMap<>();
        dadosEmail.put("nomeUsuario", usuario.getNome());
        dadosEmail.put("email", usuario.getEmail());
        dadosEmail.put("mensagem", "Atenção! Você solicitou a recuperação da senha da sua conta na Search Organic. Sua nova senha de acesso é: " + senha);

        emailService.sendEmail(dadosEmail, "Email para recuperação de senha enviado", usuario.getEmail());
    }
    
    public void sendResetEmail(Usuario usuario) throws Exception {
        Map<String, Object> dadosEmail = new HashMap<>();
        dadosEmail.put("nomeUsuario", usuario.getNome());
        dadosEmail.put("email", usuario.getEmail());
        dadosEmail.put("mensagem", "Atenção! Você redefiniu sua senha na Search Organic com sucesso!");

        emailService.sendEmail(dadosEmail, "Email para reset de senha enviado", usuario.getEmail());
    }
}
