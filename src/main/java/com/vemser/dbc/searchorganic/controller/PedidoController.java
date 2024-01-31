package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.documentacao.IPedidoController;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoUpdateDTO;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController implements IPedidoController {
    private final PedidoService pedidoService;

    @Override
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> obterPedidos(@PathVariable("idUsuario") Integer id) throws Exception {
        List<Pedido> pedidos = this.pedidoService.obterPedidoPorIdUsuario(id);
        ArrayList<PedidoDTO> pedidosDTO = this.pedidoService.preencherInformacoesArray(pedidos);
        return new ResponseEntity<>(pedidosDTO, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> obterPedido(@PathVariable("idPedido") Integer id) throws Exception {

        PedidoDTO pedidoDTO = this.pedidoService.buscaPorId(id);
        return new ResponseEntity<>(pedidoDTO, HttpStatus.OK);
    }

    @Override
    @PostMapping("/{idUsuario}")
    public ResponseEntity<PedidoDTO> criarPedido(@PathVariable("idUsuario") Integer id, @Valid @RequestBody PedidoCreateDTO pedidoCreateDTO) throws Exception {
        PedidoDTO pedidoDTO = this.pedidoService.adicionar(id, pedidoCreateDTO);
        return new ResponseEntity<>(pedidoDTO, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> update(@PathVariable("idPedido") Integer id, @Valid @RequestBody PedidoUpdateDTO pedidoAtualizar) throws Exception {
        PedidoDTO pedidoDTO = this.pedidoService.atualizarPedido(id, pedidoAtualizar);
        return new ResponseEntity<>(pedidoDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable("idPedido") Integer id) throws Exception {
        this.pedidoService.excluir(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
