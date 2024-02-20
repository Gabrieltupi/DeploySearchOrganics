package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosMesDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.repository.RelatorioMongoRepository;
import com.vemser.dbc.searchorganic.repository.RelatorioRepository;
import com.vemser.dbc.searchorganic.service.mocks.MockEmpresa;
import com.vemser.dbc.searchorganic.service.mocks.MockRelatorio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RelatorioService - Test")
class RelatorioServiceTest {
    @Mock
    private RelatorioRepository relatorioRepository;
    @Mock
    private RelatorioMongoRepository relatorioMongoRepository;
    @InjectMocks
    private RelatorioService relatorioService;

    @Test
    @DisplayName("Deve obter relatorio de produtos por preco")
    void deveObterRelatorioProdutosPreco() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<RelatorioProdutoPrecoDTO> relatorioDTOs = MockRelatorio.retornarListaRelatorioPreco();
        Page<RelatorioProdutoPrecoDTO>  relatorioPageDTO = new PageImpl<>(relatorioDTOs, pageable, relatorioDTOs.size());

        Page<Object[]> pageMock = mock(Page.class);
        List<Object[]> listaProdutos = MockRelatorio.retornarResultadoObjectRepository();

        when(pageMock.getContent()).thenReturn(listaProdutos);
        when(pageMock.getTotalElements()).thenReturn((long) listaProdutos.size());

        when(relatorioRepository.findAllProdutosByPreco(any(Pageable.class))).thenReturn(pageMock);

        Page<RelatorioProdutoPrecoDTO> dtoRetornado = relatorioService.findAllProdutosByPreco(pageable);

        Assertions.assertAll(
                () -> assertTrue(dtoRetornado.hasContent()),
                () -> assertThat(dtoRetornado.getContent().get(0).getNome()).isEqualTo(relatorioPageDTO.getContent().get(0).getNome()),
                () -> assertThat(dtoRetornado.getContent().get(2).getPreco()).isEqualTo(relatorioPageDTO.getContent().get(2).getPreco()),
                () -> assertEquals(dtoRetornado.getSize(), relatorioPageDTO.getSize())
        );

    }
    @Test
    @DisplayName("Deve obter relatorio de produtos por quantidade")
    void deveObterRelatorioProdutosQuantidade() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<RelatorioProdutoQuantidadeDTO> relatorioDTOs = MockRelatorio.retornarListaRelatorioQuantidade();
        Page<RelatorioProdutoQuantidadeDTO>  relatorioPageDTO = new PageImpl<>(relatorioDTOs, pageable, relatorioDTOs.size());

        Page<Object[]> pageMock = mock(Page.class);
        List<Object[]> listaProdutos = MockRelatorio.retornarResultadoObjectRepository();

        when(pageMock.getContent()).thenReturn(listaProdutos);
        when(pageMock.getTotalElements()).thenReturn((long) listaProdutos.size());

        when(relatorioRepository.findAllProdutosByQuantidade(any(Pageable.class))).thenReturn(pageMock);

        Page<RelatorioProdutoQuantidadeDTO> dtoRetornado = relatorioService.findAllProdutosByQuantidade(pageable);

        Assertions.assertAll(
                () -> assertTrue(dtoRetornado.hasContent()),
                () -> assertThat(dtoRetornado.getContent().get(0).getNome()).isEqualTo(relatorioPageDTO.getContent().get(0).getNome()),
                () -> assertThat(dtoRetornado.getContent().get(2).getQuantidade()).isEqualTo(relatorioPageDTO.getContent().get(2).getQuantidade()),
                () -> assertEquals(dtoRetornado.getSize(), relatorioPageDTO.getSize())
        );

    }

    @Test
    @DisplayName("Deve obter relatorio de produtos por pedido")
    void deveObterRelatorioProdutosPedido() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<RelatorioProdutoPedidosDTO> relatorioDTOs = MockRelatorio.retornarListaRelatorioPedido();
        Page<RelatorioProdutoPedidosDTO>  relatorioPageDTO = new PageImpl<>(relatorioDTOs, pageable, relatorioDTOs.size());

        Page<Object[]> pageMock = mock(Page.class);
        List<Object[]> listaProdutos = MockRelatorio.retornarResultadoObjectRepositoryInt();

        when(pageMock.getContent()).thenReturn(listaProdutos);
        when(pageMock.getTotalElements()).thenReturn((long) listaProdutos.size());

        when(relatorioRepository.findAllProdutosByPedidos(any(Pageable.class))).thenReturn(pageMock);

        Page<RelatorioProdutoPedidosDTO> dtoRetornado = relatorioService.findAllProdutosByPedidos(pageable);

        Assertions.assertAll(
                () -> assertTrue(dtoRetornado.hasContent()),
                () -> assertThat(dtoRetornado.getContent().get(0).getNome()).isEqualTo(relatorioPageDTO.getContent().get(0).getNome()),
                () -> assertThat(dtoRetornado.getContent().get(2).getPedidos()).isEqualTo(relatorioPageDTO.getContent().get(2).getPedidos()),
                () -> assertEquals(dtoRetornado.getSize(), relatorioPageDTO.getSize())
        );

    }

    @Test
    @DisplayName("Deve obter relatorio de produtos por mes")
    void deveObterRelatorioMes() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<RelatorioProdutoPedidosMesDTO> relatorioDTOs = MockRelatorio.retornarListaRelatorioMes();
        Page<RelatorioProdutoPedidosMesDTO>  relatorioPageDTO = new PageImpl<>(relatorioDTOs, pageable, relatorioDTOs.size());

        Page<Object[]> pageMock = mock(Page.class);
        List<Object[]> listaProdutos = MockRelatorio.retornarResultadoObjectRepositoryMes();

        when(pageMock.getContent()).thenReturn(listaProdutos);
        when(pageMock.getTotalElements()).thenReturn((long) listaProdutos.size());

        when(relatorioRepository.findAllProdutosByPedidosByMes(any(Pageable.class))).thenReturn(pageMock);

        Page<RelatorioProdutoPedidosMesDTO> dtoRetornado = relatorioService.findAllProdutosByPedidosByMes(pageable);

        Assertions.assertAll(
                () -> assertTrue(dtoRetornado.hasContent()),
                () -> assertThat(dtoRetornado.getContent().get(0).getNome()).isEqualTo(relatorioPageDTO.getContent().get(0).getNome()),
                () -> assertThat(dtoRetornado.getContent().get(2).getMes()).isEqualTo(relatorioPageDTO.getContent().get(2).getMes()),
                () -> assertEquals(dtoRetornado.getSize(), relatorioPageDTO.getSize())
        );

    }

    @Test
    @DisplayName("Deve obter relatorio de produtos por preco")
    void deveObterEmpresasComProdutosPeloId() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        List<RelatorioProdutoPrecoDTO> relatorioDTOs = MockRelatorio.retornarListaRelatorioPreco();
        Page<RelatorioProdutoPrecoDTO>  relatorioPageDTO = new PageImpl<>(relatorioDTOs, pageable, relatorioDTOs.size());

        Page<Object[]> pageMock = mock(Page.class);
        List<Object[]> listaProdutos = MockRelatorio.retornarResultadoObjectRepository();

        when(pageMock.getContent()).thenReturn(listaProdutos);
        when(pageMock.getTotalElements()).thenReturn((long) listaProdutos.size());

        when(relatorioRepository.findAllProdutosByPreco(any(Pageable.class))).thenReturn(pageMock);

        Page<RelatorioProdutoPrecoDTO> dtoRetornado = relatorioService.findAllProdutosByPreco(pageable);

        Assertions.assertAll(
                () -> assertTrue(dtoRetornado.hasContent()),
                () -> assertThat(dtoRetornado.getContent().get(0).getNome()).isEqualTo(relatorioPageDTO.getContent().get(0).getNome()),
                () -> assertThat(dtoRetornado.getContent().get(2).getPreco()).isEqualTo(relatorioPageDTO.getContent().get(2).getPreco()),
                () -> assertEquals(dtoRetornado.getSize(), relatorioPageDTO.getSize())
        );

    }

}