package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IEnderecoController {
    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@Valid @PathVariable("idEndereco") Integer idEndereco) throws Exception;

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos() throws Exception;

    @PostMapping
    public ResponseEntity<EnderecoDTO> adicionarEndereco(@Valid @RequestBody EnderecoCreateDTO endereco) throws Exception;

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> editarEndereco(@PathVariable("idEndereco") Integer idEndereco, @Valid @RequestBody EnderecoUpdateDTO endereco) throws Exception;

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> c(@PathVariable("idEndereco") Integer idEndereco) throws Exception;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPorUsuario(@PathVariable("idUsuario") Integer idUsuario) throws Exception;
}
