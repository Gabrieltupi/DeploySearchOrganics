package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Pedido", description = "Endpoints de Pedido")
public interface IPedidoController {
    @Operation(summary = "Obter pedido", description = "Obter pedido por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna pedido por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPedido}")
    ResponseEntity<PedidoDTO> findById(@PathVariable("idPedido") Integer idPedido) throws Exception;

    @Operation(summary = "Listar pedidos", description = "Listar todas os pedidos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todas as pedidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<List<PedidoDTO>> findAll() throws Exception;

    @Operation(summary = "Criar pedido", description = "Criar pedido por usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna pedido criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}")
    ResponseEntity<PedidoDTO> save(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody PedidoCreateDTO pedidoDto) throws Exception;

    @Operation(summary = "Editar pedido", description = "Editar pedido por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna pedido editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPedido}")
    ResponseEntity<PedidoDTO> update(@PathVariable("idPedido") Integer idPedido, @Valid @RequestBody PedidoUpdateDTO pedidoDto) throws Exception;

    @Operation(summary = "Cancelar pedido", description = "Cancelar pedido por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ""),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPedido}")
    ResponseEntity<Void> cancelarPedido(@PathVariable("idPedido") Integer idPedido) throws Exception;

    @Operation(summary = "Listar pedidos por usuário", description = "Listar pedidos por usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna pedidos por usuário"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario/{idUsuario}")
    ResponseEntity<List<PedidoDTO>> findAllByIdUsuario(@PathVariable("idUsuario") Integer idUsuario) throws Exception;
}
