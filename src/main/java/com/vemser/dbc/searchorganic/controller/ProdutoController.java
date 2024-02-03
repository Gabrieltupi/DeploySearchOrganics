package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
@Slf4j
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> findAll() throws Exception {
        return new ResponseEntity<>(produtoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable("idProduto") Integer idProduto) throws Exception {
        return new ResponseEntity<>(produtoService.findById(idProduto), HttpStatus.OK);
    }

    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<ProdutoDTO> save(@PathVariable("idEmpresa") Integer idEmpresa, @Valid @RequestBody ProdutoCreateDTO produtoDto) throws Exception {
        return new ResponseEntity<>(produtoService.save(idEmpresa, produtoDto), HttpStatus.CREATED);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable("idProduto") Integer idProduto, @RequestBody ProdutoUpdateDTO produtoDto) throws Exception {
        return new ResponseEntity<>(produtoService.update(idProduto, produtoDto), HttpStatus.OK);
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Void> delete(@PathVariable("idProduto") Integer idProduto) throws Exception {
        produtoService.delete(idProduto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<ProdutoDTO>> findAllByIdEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        return new ResponseEntity<>(produtoService.findAllByIdEmpresa(idEmpresa), HttpStatus.OK);
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProdutoDTO>> findAllByIdCategoria(@PathVariable("idCategoria") Integer idCategoria) throws Exception {
        return new ResponseEntity<>(produtoService.findAllByIdCategoria(idCategoria), HttpStatus.OK);
    }
}
