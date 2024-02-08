package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.ISenhaController;
import com.vemser.dbc.searchorganic.dto.senha.RecuperarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.ResetarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.SenhaDTO;
import com.vemser.dbc.searchorganic.service.SenhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senha")
@RequiredArgsConstructor
public class SenhaController implements ISenhaController {
    private final SenhaService senhaService;

    @PostMapping("/recuperar")
    public ResponseEntity<SenhaDTO> recover(@RequestBody RecuperarSenhaDTO senhaDto) throws Exception {
        return ResponseEntity.ok(senhaService.recover(senhaDto));
    }

    @PostMapping("/resetar")
    public ResponseEntity<SenhaDTO> reset(@RequestBody ResetarSenhaDTO senhaDto) throws Exception {
        return ResponseEntity.ok(senhaService.reset(senhaDto));
    }
}
