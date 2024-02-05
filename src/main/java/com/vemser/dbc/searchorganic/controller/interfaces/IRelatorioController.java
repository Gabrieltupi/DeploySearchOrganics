package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosMesDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

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
    ResponseEntity<Page<RelatorioProdutoPrecoDTO>> findAllProdutosByPreco(@PageableDefault(page = 0, size = 10, sort = "preco", direction = Sort.Direction.DESC) Pageable pageable) throws Exception;

    @Operation(summary = "Listar produtos por quantidade", description = "Listar produtos por quantidade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produtos por quantidade"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produto/quantidade")
    ResponseEntity<Page<RelatorioProdutoQuantidadeDTO>> findAllProdutosByQuantidade(@PageableDefault(page = 0, size = 10, sort = "quantidade", direction = Sort.Direction.DESC) Pageable pageable) throws Exception;

    @Operation(summary = "Listar produtos por pedidos", description = "Listar produtos por pedidos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produtos por pedidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produto/pedidos")
    ResponseEntity<Page<RelatorioProdutoPedidosDTO>> findAllProdutosByPedidos(@PageableDefault(page = 0, size = 10, sort = "pedidos", direction = Sort.Direction.DESC) Pageable pageable) throws Exception;

    @Operation(summary = "Listar produtos por pedidos por mês", description = "Listar produtos por pedidos por mês")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produtos por pedidos por mês"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produto/pedidos/mes")
    ResponseEntity<Page<RelatorioProdutoPedidosMesDTO>> findAllProdutosByPedidosByMes(@PageableDefault(page = 0, size = 10, sort = "mes", direction = Sort.Direction.DESC) Pageable pageable) throws Exception;
}
