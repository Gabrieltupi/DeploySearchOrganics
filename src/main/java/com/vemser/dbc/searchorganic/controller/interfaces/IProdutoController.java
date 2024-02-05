package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.produto.Imagem;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "Produto", description = "Endpoints de Produto")
public interface IProdutoController {
    @Operation(summary = "Listar produtos", description = "Listar todas os produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<Page<ProdutoDTO>> findAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception;

    @Operation(summary = "Obter produto", description = "Obter produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produto por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idProduto}")
    ResponseEntity<ProdutoDTO> findById(@PathVariable("idProduto") Integer idProduto) throws Exception;

    @Operation(summary = "Criar produto", description = "Criar produto por empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produto criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/empresa/{idEmpresa}")
    ResponseEntity<ProdutoDTO> save(@PathVariable("idEmpresa") Integer idEmpresa, @Valid @RequestBody ProdutoCreateDTO produtoDto) throws Exception;

    @Operation(summary = "Editar produto", description = "Editar produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produto editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idProduto}")
    ResponseEntity<ProdutoDTO> update(@PathVariable("idProduto") Integer idProduto, @RequestBody ProdutoUpdateDTO produtoDto) throws Exception;

    @Operation(summary = "Remover produto", description = "Remover produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ""),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    ResponseEntity<Void> delete(@PathVariable("idProduto") Integer idProduto) throws Exception;

    @Operation(summary = "Listar produtos pela empresa", description = "Listar produtos pelo id da empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produtos pela empresa"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/empresa/{idEmpresa}")
    ResponseEntity<Page<ProdutoDTO>> findAllByIdEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception;

    @Operation(summary = "Listar produtos pela categoria", description = "Listar produtos pelo id da categoria")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna produtos pela categoria"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/categoria/{idCategoria}")
    ResponseEntity<Page<ProdutoDTO>> findAllByIdCategoria(@PathVariable("idCategoria") Integer idCategoria, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception;

    @Operation(summary = "Upload de imagem", description = "Upload de imagem do produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna imagem adicionada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/imagem")
    ResponseEntity<Imagem> uploadImagem(@RequestPart("imagem") MultipartFile imagem) throws Exception;
}
