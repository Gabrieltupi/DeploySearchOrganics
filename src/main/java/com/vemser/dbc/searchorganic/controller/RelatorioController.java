package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.IRelatorioController;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosMesDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import com.vemser.dbc.searchorganic.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class RelatorioController implements IRelatorioController {
    private final RelatorioService relatorioService;

    @GetMapping("/produto/preco")
    public ResponseEntity<Page<RelatorioProdutoPrecoDTO>> findAllProdutosByPreco(@PageableDefault(page = 0, size = 10, sort = "preco", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        return new ResponseEntity<>(relatorioService.findAllProdutosByPreco(pageable), HttpStatus.OK);
    }


    @GetMapping("/produto/quantidade")
    public ResponseEntity<Page<RelatorioProdutoQuantidadeDTO>> findAllProdutosByQuantidade(@PageableDefault(page = 0, size = 10, sort = "quantidade", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        return new ResponseEntity<>(relatorioService.findAllProdutosByQuantidade(pageable), HttpStatus.OK);
    }

    @GetMapping("/produto/pedidos")
    public ResponseEntity<Page<RelatorioProdutoPedidosDTO>> findAllProdutosByPedidos(@PageableDefault(page = 0, size = 10, sort = "pedidos", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        return new ResponseEntity<>(relatorioService.findAllProdutosByPedidos(pageable), HttpStatus.OK);
    }

    @GetMapping("/produto/pedidos/mes")
    public ResponseEntity<Page<RelatorioProdutoPedidosMesDTO>> findAllProdutosByPedidosByMes(@PageableDefault(page = 0, size = 10, sort = "mes", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        return new ResponseEntity<>(relatorioService.findAllProdutosByPedidosByMes(pageable), HttpStatus.OK);
    }
}
