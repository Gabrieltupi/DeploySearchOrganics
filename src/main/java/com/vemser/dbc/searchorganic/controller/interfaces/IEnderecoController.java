package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Endereço", description = "Endpoints de Endereço")
public interface IEnderecoController {
    @Operation(summary = "Listar endereço", description = "Lista todas os endereços do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> listarEnderecos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws Exception ;

    @Operation(summary = "Retorna os endereço de um usuario", description = "endereços de um usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna endereços do usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EnderecoDTO>> listarEnderecosPorUsuario(@PathVariable("idUsuario") Integer idUsuario) throws Exception;

    @Operation(summary = "exclui os endereço de um usuario", description = "exclui endereços de um usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo à exclusão endereços do usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> removerEndereco(@PathVariable("idEndereco") Integer idEndereco) throws Exception;

    @Operation(summary = "Atualiza o endereço ", description = "atualiza o endereços pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo a alteração do endereços do usuario"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> editarEndereco(@PathVariable("idEndereco") Integer idEndereco, @Valid @RequestBody EnderecoUpdateDTO endereco) throws Exception;

    @Operation(summary = "Adicionar endereços", description = "adicionar endereços")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivoa criação de endereços"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<EnderecoDTO> adicionarEndereco(@Valid @RequestBody EnderecoCreateDTO endereco) throws Exception;

    @Operation(summary = "Retorna os endereço pelo id", description = "endereços pelo id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna endereços pelo id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> buscarEndereco(@Valid @PathVariable("idEndereco") Integer idEndereco) throws Exception;
}
