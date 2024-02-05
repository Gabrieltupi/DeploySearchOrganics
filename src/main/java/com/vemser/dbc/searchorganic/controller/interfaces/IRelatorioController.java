package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Relatório", description = "Endpoints de Relatório")
public interface IRelatorioController {
    @Operation(summary = "Listar produtos por preco", description = "Listar produtos por preco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produtos por preco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produto/preco")
    ResponseEntity<Page<RelatorioProdutoPrecoDTO>> findAllProdutosByPreco(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "desc") String sort) throws Exception;

    @Operation(summary = "Listar produtos por quantidade", description = "Listar produtos por quantidade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produtos por quantidade"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produto/quantidade")
    ResponseEntity<Page<RelatorioProdutoQuantidadeDTO>> findAllProdutosByQuantidade(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "desc") String sort) throws Exception;
}
