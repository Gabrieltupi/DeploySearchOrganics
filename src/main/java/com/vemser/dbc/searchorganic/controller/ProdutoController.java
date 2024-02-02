package com.vemser.dbc.searchorganic.controller;


import com.vemser.dbc.searchorganic.controller.documentacao.IProdutoController;
import com.vemser.dbc.searchorganic.dto.produto.Imagem;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.service.ImgurService;
import com.vemser.dbc.searchorganic.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
@Slf4j
public class ProdutoController {
    private final ProdutoService produtoService;
    private final ImgurService imgurService;

    @PostMapping //post localhost:8080/produto
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoCreateDTO produto) throws Exception {
        ProdutoDTO produtoCriado = produtoService.adicionarProduto(produto);

        return new ResponseEntity<>(produtoCriado, HttpStatus.OK);
    }

    @PostMapping("/imagem")
    public ResponseEntity<Imagem> uploadImagem(@RequestPart("imagem") MultipartFile imagem) throws Exception {
        Imagem imagemEntity = imgurService.uploadImage(imagem);

        return new ResponseEntity<>(imagemEntity, HttpStatus.OK);
    }

    @PutMapping("{idProduto}") // put localhost:8080/produto/idProduto
    public ResponseEntity<ProdutoDTO> update(@PathVariable("idProduto") Integer id, @RequestBody @Valid ProdutoUpdateDTO produto) throws Exception {
        ProdutoDTO produtoDTO = produtoService.atualizarProduto(id, produto);
        return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
    }

    @GetMapping //get localhost:8080/produto
    public List<ProdutoDTO> getAllProdutos() throws Exception {
        return produtoService.list();
    }

    @DeleteMapping("/{idProduto}") //delete localhost:8080/produto/idproduto
    public ResponseEntity<Void> delete(@PathVariable("idProduto") Integer id) throws Exception {
        try {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok().build();
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idproduto}") // get localhost:8080/produto/idproduto
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable @RequestParam("idproduto") Integer idEmpresa) throws Exception {
        ProdutoDTO produto = this.produtoService.getById(idEmpresa);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/categoria/{numeroCategoria}")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorCategoria(@PathVariable("numeroCategoria") Integer numeroCategoria) throws Exception {
        List<ProdutoDTO> produtos = this.produtoService.listarProdutosPorCategoria(numeroCategoria);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }



    @GetMapping("/loja/{idLoja}") // get localhost:8080/produto/idLoja
    public ResponseEntity<List<ProdutoDTO>> listarProdutoPorLoja(@PathVariable("idLoja") Integer idLoja) throws Exception{
        List<ProdutoDTO> produtos = this.produtoService.listarProdutosLoja(idLoja);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

}
