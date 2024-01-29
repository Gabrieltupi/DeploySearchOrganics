package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.documentacao.IEnderecoController;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
@Validated
public class EnderecoController implements IEnderecoController {
    private final EnderecoService enderecoService;

    @Override
    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@Valid @PathVariable("idEndereco") Integer idEndereco) throws Exception {
        return new ResponseEntity<>(enderecoService.buscarEndereco(idEndereco), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos() throws Exception {
        return new ResponseEntity<>(enderecoService.listarEnderecos(), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<EnderecoDTO> adicionarEndereco(@Valid @RequestBody EnderecoCreateDTO endereco) throws Exception {
        EnderecoDTO enderecoAdicionado = enderecoService.adicionarEndereco(endereco);
        return new ResponseEntity<>(enderecoAdicionado, HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> editarEndereco(@PathVariable("idEndereco") Integer idEndereco, @Valid @RequestBody EnderecoUpdateDTO endereco) throws Exception {
        return new ResponseEntity<>(enderecoService.editarEndereco(idEndereco, endereco), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> c(@PathVariable("idEndereco") Integer idEndereco) throws Exception {
        enderecoService.removerEndereco(idEndereco);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPorUsuario(@PathVariable("idUsuario") Integer idUsuario) throws Exception {
        return new ResponseEntity<>(enderecoService.listarEnderecosPorUsuario(idUsuario), HttpStatus.OK);
    }
}

