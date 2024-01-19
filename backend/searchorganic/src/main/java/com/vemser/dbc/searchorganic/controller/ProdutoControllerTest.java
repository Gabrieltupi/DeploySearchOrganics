package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoControllerTest {
    private final ProdutoService produtoService;
    public ProdutoControllerTest(ProdutoService produtoService){
        this.produtoService = produtoService;
    }
    @GetMapping
    public List<Produto> list() {
        return this.produtoService.buscarProdutos();
    }
}
