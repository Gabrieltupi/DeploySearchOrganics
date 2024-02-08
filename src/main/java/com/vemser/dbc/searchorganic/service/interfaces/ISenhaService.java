package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.senha.RecuperarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.ResetarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.SenhaDTO;
import com.vemser.dbc.searchorganic.model.Usuario;

public interface ISenhaService {
    SenhaDTO recover(RecuperarSenhaDTO senhaDto) throws Exception;

    SenhaDTO reset(ResetarSenhaDTO senhaDto) throws Exception;

    void sendRecoverEmail(Usuario usuario, String senha) throws Exception;

    void sendResetEmail(Usuario usuario) throws Exception;
}
