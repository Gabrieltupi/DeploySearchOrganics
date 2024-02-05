package com.vemser.dbc.searchorganic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.controller.interfaces.IUsuarioController;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioCreateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioLoginDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController implements IUsuarioController {
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService, ObjectMapper objectMapper) {
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> list() throws Exception {
        List<UsuarioDTO> usuarios = objectMapper.convertValue(this.usuarioService.exibirTodos(), new TypeReference<List<UsuarioDTO>>() {
        });
        ;
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> obterUmUsuario(@PathVariable("idUsuario") Integer id) throws Exception {
        Usuario usuarioEntity = this.usuarioService.obterUsuarioPorId(id);

        if (usuarioEntity != null) {
            UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
        } else {
            throw  new RegraDeNegocioException("Usuario não encontrado.");
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws Exception {

        Usuario usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, Usuario.class);
        usuarioEntity = this.usuarioService.criarUsuario(usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);

    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody UsuarioLoginDTO usuarioLoginDTO) throws Exception {
        Usuario usuarioEntity = this.usuarioService.autenticar(usuarioLoginDTO.getLogin(), usuarioLoginDTO.getSenha());
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }


    @Override
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable("idUsuario") Integer id, @Valid @RequestBody UsuarioUpdateDTO usuarioAtualizar) throws Exception {
        Usuario usuarioEntity = objectMapper.convertValue(usuarioAtualizar, Usuario.class);
        usuarioEntity = this.usuarioService.editarUsuario(id, usuarioEntity);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
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
