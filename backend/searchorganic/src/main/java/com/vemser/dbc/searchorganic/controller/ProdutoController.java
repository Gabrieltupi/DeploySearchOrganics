package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService produtoService;
//    private PropertiesReader propertiesReader;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }



    @PostMapping //post localhost:8080/produto
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoCreateDTO produto) throws Exception {
        ProdutoDTO produtoCriado= produtoService.adicionarProduto(produto);

        return new ResponseEntity<>( produtoCriado, HttpStatus.OK);
    }

    @GetMapping //get localhost:8080/produto
    public List<ProdutoDTO> getAllEnderecos() throws Exception {
        return produtoService.list();
    }

    @DeleteMapping("/{idProduto}") //delete localhost:8080/produto/idproduto
    public ResponseEntity<Void> delete(@PathVariable("idProduto") Integer id) throws Exception {
        produtoService.deleterProduto(id);
        return ResponseEntity.ok().build();
    }




}
