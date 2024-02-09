package com.vemser.dbc.searchorganic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.controller.interfaces.IUsuarioController;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioListDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController implements IUsuarioController {
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<UsuarioListDTO>> list() throws Exception {
        List<UsuarioListDTO> usuarios = this.usuarioService.exibirTodos().stream().map(UsuarioListDTO::new).toList();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> obterUsuarioPorId(@PathVariable("idUsuario") Integer id) throws Exception {
        Usuario usuarioEntity = this.usuarioService.obterUsuarioPorId(id);

        if (usuarioEntity != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioEntity);
            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
        } else {
            throw new RegraDeNegocioException("Usuario não encontrado.");
        }
    }

    @Override
    @GetMapping("/logado")
    public ResponseEntity<UsuarioDTO> obterUsuarioLogado() throws Exception {
        UsuarioDTO usuarioDTO = usuarioService.obterUsuarioLogado();
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUsuario") Integer id, @Valid @RequestBody UsuarioUpdateDTO usuarioAtualizar) throws Exception {
        Usuario usuarioEntity = objectMapper.convertValue(usuarioAtualizar, Usuario.class);
        usuarioEntity = this.usuarioService.editarUsuario(id, usuarioEntity);

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioEntity);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable("idUsuario") Integer id) throws Exception {
        this.usuarioService.removerUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> findByEmail(@PathVariable String email) throws RegraDeNegocioException {
        UsuarioDTO usuarioDTO = usuarioService.findByEmail(email);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioDTO> findByCpf(@PathVariable String cpf) throws RegraDeNegocioException {
        UsuarioDTO usuarioDTO = usuarioService.findByCpf(cpf);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }
}
