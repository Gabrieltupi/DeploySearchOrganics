package com.vemser.dbc.searchorganic.controller.documentacao;

import com.vemser.dbc.searchorganic.dto.cupom.CupomDto;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.model.Produto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Empresa", description = "Endpoints de Empresa")
public interface IEmpresaController {
    @Operation(summary = "Listar empresas", description = "Lista todas as empresas do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de empresas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> exibirEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;

    @Operation(summary = "Postar a empresa em um usuario", description = "Postar empresa à um usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para criação"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}")
    public ResponseEntity<EmpresaDTO> criarEmpresa(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody CreateEmpresaDTO empresa) throws Exception;

    @Operation(summary = "Editar o empresa pelo id", description = "Editar o empresa pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para edição"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> updateEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, @Valid @RequestBody UpdateEmpresaDTO empresaAtualizada) throws Exception;

    @Operation(summary = "Deletar a empresa por ID", description = "Deletar a empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para deleção"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;

    @Operation(summary = "Listar produtos de uma empresa", description = "Lista todas os produtos da empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    public ResponseEntity<List<Produto>> listarProdutosDaLoja(Integer idLoja) throws Exception;

    @Operation(summary = "Listar cupons de uma loja", description = "Lista todas os cupons da loja")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cupons"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEmpresa}/cupom")
    public ResponseEntity<List<Cupom>> listarCupomDaLoja(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;

    @Operation(summary = "Adicionar um cupom para a loja", description = "Adicionar cupom")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para adição de cupom"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idEmpresa}/cupom")
    public ResponseEntity<Void> adicionarCupom(@PathVariable("idEmpresa") Integer idEmpresa, @RequestBody Cupom cupom) throws Exception;

    @Operation(summary = "Listar cupons de uma empresa", description = "Lista todas os cupons da empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cupons"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEmpresa}/cupons")
    public ResponseEntity<List<Cupom>> listarCuponsDaEmpresa(@PathVariable("idEmpresa") Integer idEmpresa);

    @Operation(summary = "remover cupons", description = "Deletar um cupom")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para a exclusão de um cupons"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/cupom/{idCupom}")
    public ResponseEntity<Void> removerCupom(@PathVariable("idCupom") Integer idCupom);

    @Operation(summary = "Altera um cupons de uma empresa", description = "Altera todas os cupons da empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo à um cupom editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/cupom/{idCupom}")
    public ResponseEntity<CupomDto> atualizarCupom(@PathVariable("idCupom") Integer idCupom, @RequestBody Cupom cupom) throws Exception;

    @Operation(summary = "Listar todos cupons", description = "Lista todas os cupons ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cupons"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/cupom")
    public ResponseEntity<List<Cupom>> listarCupom() throws Exception;
}


