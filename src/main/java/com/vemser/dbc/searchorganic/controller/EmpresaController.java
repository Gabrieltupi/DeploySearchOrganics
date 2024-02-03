package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.IEmpresaController;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/empresa")
@RequiredArgsConstructor
public class EmpresaController implements IEmpresaController {
    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<Page<EmpresaDTO>> findAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(empresaService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> findById(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        return new ResponseEntity<>(empresaService.findById(idEmpresa), HttpStatus.OK);
    }

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<EmpresaDTO> save(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody CreateEmpresaDTO empresaDto) throws Exception {
        return new ResponseEntity<>(empresaService.save(idUsuario, empresaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> update(@PathVariable("idEmpresa") Integer idEmpresa, @RequestBody UpdateEmpresaDTO empresaDto) throws Exception {
        return new ResponseEntity<>(empresaService.update(idEmpresa, empresaDto), HttpStatus.OK);
    }

    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> delete(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        empresaService.delete(idEmpresa);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/produtos")
    public ResponseEntity<Page<EmpresaProdutosDTO>> findAllWithProdutos(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(empresaService.findAllWithProdutos(pageable), HttpStatus.OK);
    }

    @GetMapping("/{idEmpresa}/produtos")
    public ResponseEntity<EmpresaProdutosDTO> findByIdWithProdutos(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        return new ResponseEntity<>(empresaService.findByIdWithProdutos(idEmpresa), HttpStatus.OK);
    }
}
