package com.vemser.dbc.searchorganic.controller.documentacao;

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
    @Operation(summary = "Listar pedidos", description = "Lista todas os pedidos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pedidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> obterPedidos(@PathVariable("idUsuario") Integer id) throws Exception;

    @Operation(summary = "Retorna o pedido", description = "retorna o pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o pedidos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> obterPedido(@PathVariable("idPedido") Integer id) throws Exception;

    @Operation(summary = "Cria um pedido através do id do usuario", description = "cria um pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para a criação de um pedido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}")
    public ResponseEntity<PedidoDTO> criarPedido(@PathVariable("idUsuario") Integer id, @Valid @RequestBody PedidoCreateDTO pedidoCreateDTO) throws Exception;

    @Operation(summary = "Altera um pedido através do id do pedido", description = "altera um pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para a alteração de um pedido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> update(@PathVariable("idPedido") Integer id, @Valid @RequestBody PedidoUpdateDTO pedidoAtualizar) throws Exception;

    @Operation(summary = "Deleta um pedido através do id do pedido", description = "deleta um pedido")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna positivo para a exclusão de um pedido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable("idPedido") Integer id) throws Exception;
}
