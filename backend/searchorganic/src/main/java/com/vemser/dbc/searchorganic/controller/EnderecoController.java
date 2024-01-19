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

    private EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService){
        this.enderecoService = enderecoService;
    }


    @GetMapping// GET /enderecos
    public ResponseEntity<List<Endereco>> listaDeEnderecos() {
        return new ResponseEntity<>(enderecoService.getEnderecos(), HttpStatus.OK);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Endereco> listarPorIdEUsario(@Valid @PathVariable("idUsuario") Integer idUsuario){
        return new ResponseEntity<>(enderecoService.getEndereco(idUsuario), HttpStatus.OK);
    }

    @DeleteMapping("/idUsuario")
    public ResponseEntity<Endereco> excluirUsuario(@Valid @PathVariable("idUsuario") Integer idUsuario){
    return new ResponseEntity<>(enderecoService.excluirPorIdUsuario(idUsuario),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> atualizarUsuario(@Valid @PathVariable("id") Integer id,
                                                    @RequestBody Endereco novoEndereco) {
        boolean sucesso = enderecoService.atualizarEndereco(id, novoEndereco);

        return sucesso
                ? ResponseEntity.ok(true)
                : ResponseEntity.status(sucesso ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST).body(false);
    }

    @PostMapping
    public ResponseEntity<Endereco> adicionarEndereco(@Valid @RequestBody Endereco endereco) {
        Endereco enderecoAdicionado = enderecoService.adicionarEndereco(endereco);
        return new ResponseEntity<>(enderecoAdicionado, HttpStatus.OK);
    }







}
