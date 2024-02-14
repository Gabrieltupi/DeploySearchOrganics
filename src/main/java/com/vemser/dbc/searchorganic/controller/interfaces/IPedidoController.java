package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
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

    @Operation(summary = "Pagar o pedido", description = "Pagar o pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Produto pago"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/pagar/{idPedido}")
    public ResponseEntity<PedidoDTO> pagar(@PathVariable("idPedido") Integer idPedido, @Valid @RequestBody PagamentoDTO pagamentoDTO) throws Exception;

    @Operation(summary = "Entrega do pedido", description = "Entrega do pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Pedido entregue"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/entregue/{idPedido}")
    public ResponseEntity<PedidoDTO> entregue(@PathVariable("idPedido") Integer idPedido) throws Exception;
    
    @Operation(summary = "Editar Status", description = "Editar o status do pedido pelo id da empresa e o id do pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Status editado pedidos por usuário!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEmpresa}/pedido/{idPedido}/status")
    public ResponseEntity<PedidoEmpresaDTO> updatePedidoStatus(@PathVariable("idEmpresa") Integer idEmpresa, @PathVariable("idPedido") Integer idPedido, @RequestParam("novoStatus") StatusPedido novoStatus) throws Exception;
    @Operation(summary = "Editar Codigo de rastreio", description = "Editar o codigo de rastreio com base no id do pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Codigo de rastreio editado!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}/rastreio")
    public ResponseEntity<PedidoRastreioDTO> atualizarCodigoDeRastreio(@PathVariable("id") Integer idPedido,
                                                                       @RequestParam(value = "codigoRastreio",required = false) String codigoRastreio,
                                                                       @RequestParam("idEmpresa") Integer idEmpresa) throws Exception;
}
