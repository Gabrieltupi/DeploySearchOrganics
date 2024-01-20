package com.vemser.dbc.searchorganic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoUpdateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioCreateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioLoginDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioUpdateDTO;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.PedidoService;
import com.vemser.dbc.searchorganic.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    public PedidoController(PedidoService pedidoService, ObjectMapper objectMapper, UsuarioService usuarioService){
        this.pedidoService = pedidoService;
        this.objectMapper = objectMapper;
        this.usuarioService = usuarioService;
    }
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> obterPedidos(@PathVariable("idUsuario") Integer id) throws Exception {
        List<PedidoDTO> pedidos = this.pedidoService.obterPedidoPorIdUsuario(id);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> obterPedido(@PathVariable("idPedido") Integer id) throws Exception {
        Pedido pedidoEntity = this.pedidoService.obterPorId(id);
        PedidoDTO pedidoDTO = this.pedidoService.preencherInformacoes(pedidoEntity);
        return new ResponseEntity<>(pedidoDTO, HttpStatus.OK);
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<PedidoDTO> criarPedido(@PathVariable("idUsuario") Integer id, @Valid @RequestBody PedidoCreateDTO pedidoCreateDTO) throws Exception {
        usuarioService.obterUsuarioPorId(id);
        Pedido pedidoEntity = objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
        pedidoEntity.setIdUsuario(id);
        pedidoEntity = this.pedidoService.adicionar(pedidoEntity);

        PedidoDTO pedidoDTO = this.pedidoService.preencherInformacoes(pedidoEntity);
        return new ResponseEntity<>(pedidoDTO, HttpStatus.CREATED);
    }


    @PutMapping("/{idPedido}")
    public  ResponseEntity<PedidoDTO> update(@PathVariable("idPedido") Integer id, @Valid @RequestBody PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = this.pedidoService.atualizarPedido(id, pedidoAtualizar);
        PedidoDTO pedidoDTO = this.pedidoService.preencherInformacoes(pedidoEntity);
        return new  ResponseEntity<>(pedidoDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable("idPedido") Integer id) throws Exception {
        this.pedidoService.excluir(id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
