package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
@Validated
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping // GET /endereco
    public ResponseEntity<List<Endereco>> listaDeEnderecos() {
        return new ResponseEntity<>(enderecoService.getEnderecos(), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Endereco> listarEnderecoPorIdDeUsuario(@Valid @PathVariable("idUsuario") Integer idUsuario) {
        return new ResponseEntity<>(enderecoService.getEndereco(idUsuario), HttpStatus.OK);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> excluirEnderecosPorIdUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        enderecoService.excluirEndereco(idUsuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> atualizarEndereco(@PathVariable("id") Integer id,
                                                      @Valid @RequestBody Endereco novoEndereco) {
        return new ResponseEntity<>(enderecoService.atualizarEndereco(id, novoEndereco), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Endereco> adicionarEndereco(@Valid @RequestBody Endereco endereco) {
        Endereco enderecoAdicionado = enderecoService.adicionarEndereco(endereco);
        return new ResponseEntity<>(enderecoAdicionado, HttpStatus.OK);
    }
}

