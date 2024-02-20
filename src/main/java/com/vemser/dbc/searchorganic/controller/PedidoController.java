package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.IPedidoController;
import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.service.PedidoService;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController implements IPedidoController {
    private final PedidoService pedidoService;

    @Override
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> findById(@PathVariable("idPedido") Integer idPedido) throws Exception {
        return new ResponseEntity<>(pedidoService.getById(idPedido), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() throws Exception {
        return new ResponseEntity<>(pedidoService.findAll(), HttpStatus.OK);
    }

    @Override
    @PostMapping("/{idUsuario}")
    public ResponseEntity<PedidoDTO> save(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody PedidoCreateDTO pedidoDto) throws Exception {
        return new ResponseEntity<>(pedidoService.save(idUsuario, pedidoDto), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> update(@PathVariable("idPedido") Integer idPedido, @Valid @RequestBody PedidoUpdateDTO pedidoDto) throws Exception {
        return new ResponseEntity<>(pedidoService.update(idPedido, pedidoDto), HttpStatus.OK);
    }

    @Override
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> findAllByIdUsuario(@PathVariable("idUsuario") Integer idUsuario) throws Exception {
        return new ResponseEntity<>(pedidoService.findAllByIdUsuario(idUsuario), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable("idPedido") Integer idPedido) throws Exception {
        this.pedidoService.cancelarPedido(idPedido);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/pagar/{idPedido}")
    public ResponseEntity<PedidoDTO> pagar(@PathVariable("idPedido") Integer idPedido, @Valid @RequestBody PagamentoDTO pagamentoDTO) throws Exception {
        return new ResponseEntity<>(pedidoService.pagamento(idPedido, pagamentoDTO), HttpStatus.CREATED);
    }
    @PostMapping("/entregue/{idPedido}")
    public ResponseEntity<PedidoDTO> entregue(@PathVariable("idPedido") Integer idPedido) throws Exception {
        return new ResponseEntity<>(pedidoService.entregue(idPedido), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/rastreio")
    public ResponseEntity<PedidoRastreioDTO> atualizarCodigoDeRastreio(@PathVariable("id") Integer idPedido,
                                                                       @RequestParam(value = "codigoRastreio",required = false) String codigoRastreio,
                                                                       @RequestParam("idEmpresa") Integer idEmpresa) throws Exception {
        PedidoRastreioDTO pedidoRastreioDTO = pedidoService.updateCodigoDeRastreio(idPedido, codigoRastreio);
        return ResponseEntity.ok(pedidoRastreioDTO);
    }

    @PutMapping("/{idEmpresa}/pedido/{idPedido}/status")
    public ResponseEntity<PedidoEmpresaDTO> updatePedidoStatus(@PathVariable("idEmpresa") Integer idEmpresa, @PathVariable("idPedido") Integer idPedido, @RequestParam("novoStatus") StatusPedido novoStatus) throws Exception {

        PedidoEmpresaDTO pedidoAtualizado = pedidoService.updatePedidoStatus(idPedido, novoStatus, idEmpresa);

        return new ResponseEntity<>(pedidoAtualizado, HttpStatus.OK);
    }
}
