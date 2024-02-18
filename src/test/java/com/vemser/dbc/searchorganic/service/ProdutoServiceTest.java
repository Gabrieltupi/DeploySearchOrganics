package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.service.mocks.MocksProduto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Produto Service-Teste")
class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EmpresaService empresaService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ProdutoService produtoService;


    @Test
    @DisplayName("CriaçãoDoProdutoDeveSerSucesso")
    public void criaçãoDoProdutoDeveSerSucesso() throws Exception {
        Integer idEmpresa = 1;
        ProdutoCreateDTO produtoCreateDTO= MocksProduto.retornarProdutoCreateDto();
        EmpresaDTO empresaDTO= MocksProduto.retornarEmpresaDto();
        Produto produto= MocksProduto.retornarProdutoEntity();
        ProdutoDTO produtoDTO=MocksProduto.retornarProdutoDTO();

        when(empresaService.findById(idEmpresa)).thenReturn(empresaDTO);
        when(objectMapper.convertValue(produtoCreateDTO, Produto.class)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produto);
        when(objectMapper.convertValue(produto, ProdutoDTO.class)).thenReturn(produtoDTO);

        ProdutoDTO produtoDTOCriado = produtoService.save(idEmpresa, produtoCreateDTO);

        assertNotNull(produtoDTOCriado);
        assertEquals(produtoDTO, produtoDTOCriado);
        verify(empresaService).findById(idEmpresa);
        verify(objectMapper).convertValue(produtoCreateDTO, Produto.class);
        verify(produtoRepository).save(produto);
        verify(objectMapper).convertValue(produto, ProdutoDTO.class);

    }


    @Test
    @DisplayName("criacaoDoProdutoDeveFalhar")
    public void criacaoDoProdutoDeveFalhar() throws Exception {
        Integer idEmpresa = 1;
        ProdutoCreateDTO produtoCreateDTO = MocksProduto.retornarProdutoCreateDto();

        when(empresaService.findById(idEmpresa)).thenThrow(new RegraDeNegocioException("Empresa não encontrada"));

        assertThrows(RegraDeNegocioException.class, () -> {
            produtoService.save(idEmpresa, produtoCreateDTO);
        });

        verify(empresaService).findById(idEmpresa);
        verifyNoInteractions(produtoRepository);

    }

    @Test
    @DisplayName("atualizacaoDoProdutoDeveTerSucesso")
    public void atualizacaoDoProdutoDeveTerSucesso() throws Exception {
        ProdutoUpdateDTO novoProduto= MocksProduto.retornarProdutoUpdateDto();
        ProdutoDTO produtoExistente=MocksProduto.retornarProdutoDTO();
        Produto produtoConvertido= MocksProduto.retornarProdutoEntity();

        when(produtoService.findById(produtoExistente.getIdProduto())).thenReturn(produtoExistente);
        when(objectMapper.convertValue(novoProduto, Produto.class)).thenReturn(produtoConvertido);
        when(produtoRepository.save(produtoConvertido)).thenReturn(produtoConvertido);

        ProdutoDTO produtoDTOAtualizado = produtoService.update(produtoExistente.getIdProduto(), novoProduto);

        assertEquals(produtoConvertido.getIdProduto(), produtoDTOAtualizado.getIdProduto());
        assertNotEquals(novoProduto,produtoExistente);

    }

    @Test
    @DisplayName("sucessoListagemProdutoPorId")
    public void sucessoListagemProdutoPorId() throws Exception {
        ProdutoDTO produtoDTO= MocksProduto.retornarProdutoDTO();
        Produto produtoExistente = MocksProduto.retornarProdutoEntity();

        when(produtoRepository.findById(produtoExistente.getIdProduto())).thenReturn(Optional.of(produtoExistente));
        when(objectMapper.convertValue(produtoExistente, ProdutoDTO.class)).thenReturn(produtoDTO);


        ProdutoDTO produtoDTOBuscado = produtoService.findById(produtoExistente.getIdProduto());

        assertEquals(produtoExistente.getIdProduto(), produtoDTOBuscado.getIdProduto());
        assertEquals(produtoExistente.getIdEmpresa(), produtoDTOBuscado.getIdEmpresa());
        assertEquals(produtoExistente.getNome(), produtoDTOBuscado.getNome());
        assertEquals(produtoExistente.getDescricao(), produtoDTOBuscado.getDescricao());

    }


    @Test
    @DisplayName("sucessoAoListarProdutos")
    public void sucessoAoListarProdutos() throws Exception {
        List<Produto> produtos= MocksProduto.retornarListaProdutos();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Produto> listaMock = new PageImpl<>(produtos, pageable, MocksProduto.retornarListaProdutos().size());

        when(produtoRepository.findAll(pageable)).thenReturn(listaMock);

        Page<ProdutoDTO> produtoDTOPage = produtoService.findAll(pageable);
        assertEquals(MocksProduto.retornarListaProdutos().size(), produtoDTOPage.getContent().size());


    }

    @Test
    @DisplayName("erroAoListarProdutos")
    public void erroAoListarProdutos() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Produto> listaMock = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(produtoRepository.findAll(pageable)).thenReturn(listaMock);

        Page<ProdutoDTO> produtoDTOPage = produtoService.findAll(pageable);

        assertEquals(0, produtoDTOPage.getContent().size());
    }


    @Test
    @DisplayName("falhaAoProcurarId")
    public void falhaAoProcurarId() throws Exception {
        Integer idProdutoNaoExistente = 999;

        when(produtoRepository.findById(idProdutoNaoExistente)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            produtoService.findById(idProdutoNaoExistente);
        });
    }

    @Test
    @DisplayName("sucessoAoDeletar")
    public void sucessoAoDeletar() throws Exception {
        ProdutoDTO produtoDTOExistente = MocksProduto.retornarProdutoDTO();
        Empresa empresa = MocksProduto.retornarEmpresaEntity();

        when(produtoService.findById(produtoDTOExistente.getIdProduto())).thenReturn(produtoDTOExistente);
        when(empresaService.getById(produtoDTOExistente.getIdEmpresa())).thenReturn(empresa);
        when(usuarioService.getIdLoggedUser()).thenReturn(empresa.getIdUsuario());

        produtoService.delete(produtoDTOExistente.getIdProduto());

        verify(produtoRepository, times(1)).deleteById(produtoDTOExistente.getIdProduto());
    }

    @Test
    @DisplayName("sucessoUsuarioAdmin")
    public void sucessoUsuarioAdmin() {
        int idUsuarioLogado = 1;

        when(usuarioService.getIdLoggedUser()).thenReturn(idUsuarioLogado);
        when(produtoRepository.existsAdminCargoByUserId(idUsuarioLogado)).thenReturn(1);

        assertTrue(produtoService.isAdmin());
    }

    @Test
    @DisplayName("falhaUsuarioAdmin")
    public void falhaUsuarioAdmin() {
        // Mock do ID do usuário
        int idUsuarioLogado = 2;

        when(usuarioService.getIdLoggedUser()).thenReturn(idUsuarioLogado);
        when(produtoRepository.existsAdminCargoByUserId(idUsuarioLogado)).thenReturn(0);

        assertFalse(produtoService.isAdmin());
    }

    @Test
    @DisplayName("sucessoListarProdutosPorEmpresa")
    public void sucessoListarProdutosPorEmpresa() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        EmpresaDTO empresa = MocksProduto.retornarEmpresaDto();
        List<Produto> produtosDoRepositorio = MocksProduto.retornarListaProdutos();
        Page<Produto> paginaProdutos = new PageImpl<>(produtosDoRepositorio, pageable, produtosDoRepositorio.size());

        when(empresaService.findById(empresa.getIdEmpresa())).thenReturn(empresa);
        when(produtoRepository.findAllByIdEmpresa(empresa.getIdEmpresa(), pageable)).thenReturn(paginaProdutos);

        Page<ProdutoDTO> resultado = produtoService.findAllByIdEmpresa(empresa.getIdEmpresa(), pageable);

        assertEquals(paginaProdutos.getTotalElements(), resultado.getTotalElements());
    }


    @Test
    @DisplayName("falhaListarProdutosPorEmpresa")
    public void falhaListarProdutosPorEmpresa() throws Exception {
        int idEmpresaInvalido = 999;
        Pageable pageable = PageRequest.of(0, 10);

        when(empresaService.findById(idEmpresaInvalido)).thenThrow(RegraDeNegocioException.class);

        assertThrows(RegraDeNegocioException.class, () -> {
            produtoService.findAllByIdEmpresa(idEmpresaInvalido, pageable);
        });
    }



    @Test
    @DisplayName("sucessoListaProdutosPorCategoria")
    public void sucessoListaProdutosPorCategoria() throws Exception {
        int idCategoria = 1;
        Pageable pageable = PageRequest.of(0, 10);
        List<Produto> produtosDoRepositorio = MocksProduto.retornarListaProdutos();
        Page<Produto> paginaProdutos = new PageImpl<>(produtosDoRepositorio, pageable, produtosDoRepositorio.size());

        when(produtoRepository.findAllByIdCategoria(idCategoria, pageable)).thenReturn(paginaProdutos);

        Page<ProdutoDTO> resultado = produtoService.findAllByIdCategoria(idCategoria, pageable);

        assertEquals(paginaProdutos.getTotalElements(), resultado.getTotalElements());
    }


    @Test
    @DisplayName("sucessoEmailProdutoPedido")
    public void sucessoEmailProdutoPedido() {

    }


}


