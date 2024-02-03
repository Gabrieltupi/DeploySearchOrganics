package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.IProdutoController;
import com.vemser.dbc.searchorganic.dto.produto.Imagem;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.service.ImgurService;
import com.vemser.dbc.searchorganic.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProdutoController implements IProdutoController {
    private final ProdutoService produtoService;
    private final ImgurService imgurService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(produtoService.findAll(pageable), HttpStatus.OK);
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
    public ResponseEntity<Page<ProdutoDTO>> findAllByIdEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(produtoService.findAllByIdEmpresa(idEmpresa, pageable), HttpStatus.OK);
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<Page<ProdutoDTO>> findAllByIdCategoria(@PathVariable("idCategoria") Integer idCategoria, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(produtoService.findAllByIdCategoria(idCategoria, pageable), HttpStatus.OK);
    }

    @PostMapping("/imagem")
    public ResponseEntity<Imagem> uploadImagem(@RequestPart("imagem") MultipartFile imagem) throws Exception {
        Imagem imagemEntity = imgurService.uploadImage(imagem);

        return new ResponseEntity<>(imagemEntity, HttpStatus.OK);
    }
}
