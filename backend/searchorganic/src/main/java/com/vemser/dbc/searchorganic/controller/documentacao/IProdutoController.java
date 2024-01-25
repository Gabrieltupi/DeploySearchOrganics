package com.vemser.dbc.searchorganic.controller.documentacao;

import com.vemser.dbc.searchorganic.dto.produto.Imagem;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.model.Produto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface IProdutoController {

    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pessoas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping //post localhost:8080/produto
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoCreateDTO produto ) throws Exception;
    @PostMapping("/imagem")
    public ResponseEntity<Imagem> uploadImagem(@RequestPart("imagem") MultipartFile imagem) throws Exception;

    @PutMapping("{idProduto}") // put localhost:8080/produto/idProduto
    public ResponseEntity<ProdutoDTO> update (@PathVariable("idProduto") Integer id, @RequestBody @Valid ProdutoUpdateDTO produto) throws Exception;

    @GetMapping //get localhost:8080/produto
    public List<ProdutoDTO> getAllProdutos() throws Exception;

    @DeleteMapping("/{idProduto}") //delete localhost:8080/produto/idproduto
    public ResponseEntity<Void> delete(@PathVariable("idProduto") Integer id) throws Exception;

    @GetMapping("/{idproduto}") // get localhost:8080/produto/idproduto
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable ("idproduto")Integer idEmpresa) throws Exception;

    @GetMapping("/categoria/{numeroCategoria}")
    public ResponseEntity<List<Produto>> listarProdutosPorCategoria(@PathVariable("numeroCategoria") Integer numeroCategoria) throws Exception;

    @GetMapping("/loja/{idLoja}") // get localhost:8080/produto/idLoja
    public ResponseEntity<List<Produto>> listarProdutoPorLoja(@PathVariable("idLoja") Integer idLoja) throws Exception;


}
