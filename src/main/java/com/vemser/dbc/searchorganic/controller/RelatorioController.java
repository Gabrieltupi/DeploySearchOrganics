package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.IRelatorioController;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import com.vemser.dbc.searchorganic.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class RelatorioController implements IRelatorioController {
    private final RelatorioService relatorioService;

    @GetMapping("/produto/preco")
    public ResponseEntity<Page<RelatorioProdutoPrecoDTO>> findAllProdutosByPreco(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "desc") String sort) throws Exception {
        Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "preco"));
        return new ResponseEntity<>(relatorioService.findAllProdutosByPreco(pageable), HttpStatus.OK);
    }

    @GetMapping("/produto/quantidade")
    public ResponseEntity<Page<RelatorioProdutoQuantidadeDTO>> findAllProdutosByQuantidade(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "desc") String sort) throws Exception {
        Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "quantidade"));
        return new ResponseEntity<>(relatorioService.findAllProdutosByQuantidade(pageable), HttpStatus.OK);
    }
}
