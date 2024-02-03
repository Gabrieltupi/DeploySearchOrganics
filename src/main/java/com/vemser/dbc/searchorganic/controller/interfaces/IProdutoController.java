package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.produto.Imagem;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.model.Produto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Produto", description = "Endpoints de Produto")
public interface IProdutoController {
    @Operation(summary = "Adicionar produto", description = "Adicionando produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Adiciona produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping //post localhost:8080/produto
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoCreateDTO produto) throws Exception;

    @Operation(summary = "Adicionar imagem", description = "Adiciona imagem")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Adiciona imagem"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/imagem")
    public ResponseEntity<Imagem> uploadImagem(@RequestPart("imagem") MultipartFile imagem) throws Exception;

    @Operation(summary = "Altera o produto pelo id", description = "Alterando produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "alterando produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("{idProduto}") // put localhost:8080/produto/idProduto
    public ResponseEntity<ProdutoDTO> update(@PathVariable("idProduto") Integer id, @RequestBody @Valid ProdutoUpdateDTO produto) throws Exception;

    @Operation(summary = "Listar todos os produtos", description = "lista de produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "lista de produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping //get localhost:8080/produto
    public List<ProdutoDTO> getAllProdutos() throws Exception;

    @Operation(summary = "Deletar um produto", description = "Deletando produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "deletado o produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idProduto}") //delete localhost:8080/produto/idproduto
    public ResponseEntity<Void> delete(@PathVariable("idProduto") Integer id) throws Exception;

    @Operation(summary = "Mostrar produto pelo id", description = "Mostrando produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Mostra produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idproduto}") // get localhost:8080/produto/idproduto
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable("idproduto") Integer idEmpresa) throws Exception;

    @Operation(summary = "Listando produtos por categoria", description = "Listando produtos por categoria")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de produtos por categoria"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/categoria/{numeroCategoria}")
    public ResponseEntity<List<Produto>> listarProdutosPorCategoria(@PathVariable("numeroCategoria") Integer numeroCategoria) throws Exception;

    @Operation(summary = "Listando produtos por loja", description = "Listando produtos por loja")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista de produtos por loja"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/loja/{idLoja}") // get localhost:8080/produto/idLoja
    public ResponseEntity<List<Produto>> listarProdutoPorLoja(@PathVariable("idLoja") Integer idLoja) throws Exception;
}
