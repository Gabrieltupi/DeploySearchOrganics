package com.vemser.dbc.searchorganic.controller;


import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.service.ProdutoService;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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


    @PutMapping("{idProduto}") // put localhost:8080/produto/idProduto
    public ResponseEntity<ProdutoDTO> update (@PathVariable("idProduto") Integer id,@Valid @RequestBody ProdutoCreateDTO produto) throws Exception{
        ProdutoDTO produtoDTO=produtoService.atualizarProduto(id,produto);
        return new ResponseEntity<>(produtoDTO,HttpStatus.OK);
    }

    @GetMapping //get localhost:8080/produto
    public List<ProdutoDTO> getAllProdutos() throws Exception {
        return produtoService.list();
    }

    @DeleteMapping("/{idProduto}") //delete localhost:8080/produto/idproduto
    public ResponseEntity<Void> delete(@PathVariable("idProduto") Integer id) throws Exception {
        produtoService.deleterProduto(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{idproduto}") // get localhost:8080/produto/idproduto
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable ("idproduto")Integer idEmpresa) throws Exception{
        Produto produto= this.produtoService.buscarProdutoPorId(idEmpresa);
        return new ResponseEntity<>(produto,HttpStatus.OK);
    }

    @GetMapping("/categoria/{numeroCategoria}")
    public ResponseEntity<Produto> listarProdutosPorCategoria(@PathVariable("numeroCategoria") Integer numeroCategoria) throws Exception {
        Produto produto = this.produtoService.listarProdutosPorCategoria(numeroCategoria);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }



    @GetMapping("/loja/{idLoja}") // get localhost:8080/produto/idLoja
    public ResponseEntity<List<Produto>> listarProdutoPorLoja(@PathVariable("idLoja") Integer idLoja) throws Exception {
        List<Produto> produtos = this.produtoService.listarProdutosLoja(idLoja);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }




}
