package com.vemser.dbc.searchorganic.dto.usuario;

import com.vemser.dbc.searchorganic.model.Usuario;

public class UsuarioDTOConverter {
    public static UsuarioDTO convertUsuarioToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setSobrenome(usuario.getSobrenome());
        usuarioDTO.setDataNascimento(usuario.getDataNascimento());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setTipoAtivo(usuario.getTipoAtivo());
        return usuarioDTO;
    }
}
