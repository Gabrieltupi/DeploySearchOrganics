package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.repository.CupomRepository;
import com.vemser.dbc.searchorganic.service.mocks.MockCupom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CupomService - Test")
class CupomServiceTest {
    @InjectMocks
    private CupomService cupomService;

    @Mock
    CupomRepository cupomRepository;

    @Mock
    UsuarioService usuarioService;

    @Mock
    private ObjectMapper objectMapper;

    private final Cupom cupomMock = MockCupom.retornarCupom();
    private final CupomDTO cupomDTOMock = MockCupom.retornarCupomDTO(cupomMock);
    private final CreateCupomDTO cupomCreateDTOMock = MockCupom.retornarCupomCreateDTO(cupomMock);
    private final UpdateCupomDTO cupomUpdateDTOMock = MockCupom.retornarCupomUpdateDTO(cupomMock);
    private final List<Cupom> cuponsMock = MockCupom.retornarCupons(cupomMock);
    private final List<CupomDTO> cuponssDTOMock = MockCupom.retornarCuponsDTO(cupomDTOMock);

    @Test
    @DisplayName("Deveria listar cupons com sucesso")
    public void deveriaListarCuponsComSucesso() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cupom> listaMock = new PageImpl<>(cuponsMock, pageable, cuponsMock.size());

        when(objectMapper.convertValue(any(Cupom.class), eq(CupomDTO.class))).thenReturn(cupomDTOMock);
        when(cupomRepository.findAll(pageable)).thenReturn(listaMock);

        Page<CupomDTO> listaDTORetornada = cupomService.findAll(pageable);

        assertNotNull(listaDTORetornada);
        assertEquals(cuponssDTOMock.size(), listaDTORetornada.getContent().size());
        assertIterableEquals(cuponssDTOMock, listaDTORetornada.getContent());
    }

    @Test
    @DisplayName("Deveria retornar um cupom dto por id com sucesso")
    public void deveriaRetornarCupomDTOPorId() throws Exception {
        when(cupomRepository.findById(anyInt())).thenReturn(Optional.of(cupomMock));
        when(objectMapper.convertValue(cupomMock, CupomDTO.class)).thenReturn(cupomDTOMock);

        CupomDTO enderecoRetornado = cupomService.findById(cupomMock.getIdCupom());

        assertNotNull(enderecoRetornado);
        assertEquals(cupomDTOMock, enderecoRetornado);
    }

    @Test
    @DisplayName("Não deveria retornar um cupom por id inexistente")
    public void naoDeveriaRetornarEnderecoDTODeOutroUsuario() {
        when(cupomRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> cupomService.findById(cupomMock.getIdCupom()));
    }

    @Test
    @DisplayName("Deveria criar um cupom com sucesso")
    public void deveriaCriarCupom() throws Exception {
        when(objectMapper.convertValue(cupomCreateDTOMock, Cupom.class)).thenReturn(cupomMock);
        when(cupomRepository.save(any())).thenReturn(cupomMock);
        when(objectMapper.convertValue(cupomMock, CupomDTO.class)).thenReturn(cupomDTOMock);

        CupomDTO cupomCriado = cupomService.save(cupomMock.getIdEmpresa(), cupomCreateDTOMock);

        assertNotNull(cupomCriado);
        assertEquals(cupomDTOMock, cupomCriado);
    }

    @Test
    @DisplayName("Deveria atualizar um endereço com sucesso")
    public void deveriaAtualizarEndereco() throws Exception {
        CupomDTO cupomAntigo = new CupomDTO();
        BeanUtils.copyProperties(cupomDTOMock, cupomAntigo);
        cupomDTOMock.setDescricao("Nova Descrição");

        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.of(cupomMock));
        when(cupomRepository.save(any())).thenReturn(cupomMock);
        when(usuarioService.isAdmin()).thenReturn(true);
        when(objectMapper.convertValue(cupomUpdateDTOMock, Cupom.class)).thenReturn(cupomMock);
        when(objectMapper.convertValue(cupomMock, CupomDTO.class)).thenReturn(cupomAntigo);

        CupomDTO cupomAtualizado = cupomService.update(cupomMock.getIdCupom(), cupomUpdateDTOMock);

        assertNotNull(cupomAtualizado);
        assertNotEquals(cupomDTOMock, cupomAtualizado);
        assertNotEquals(cupomDTOMock.getDescricao(), cupomAtualizado.getDescricao());
    }

    @Test
    @DisplayName("Não deveria atualizar um endereço de outro usuário")
    public void naoDeveriaAtualizarCupomOutroUsuario() throws Exception {
        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.of(cupomMock));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> cupomService.update(cupomMock.getIdCupom(), cupomUpdateDTOMock));
        assertEquals("O cupom informado não está associado ao usuário logado.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deveria atualizar um endereço de outra empresa")
    public void naoDeveriaAtualizarCupomOutraEmpresa() {
        cupomUpdateDTOMock.setIdEmpresa(1234);

        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.of(cupomMock));

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> cupomService.update(cupomMock.getIdCupom(), cupomUpdateDTOMock));
        assertEquals("Não foi encontrado um cupom válido associado na empresa informada.", exception.getMessage());
    }

    @Test
    @DisplayName("Não deveria atualizar um cupom inexistente")
    public void naoDeveriaAtualizarCupomInexistente() {
        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> cupomService.update(cupomMock.getIdCupom(), cupomUpdateDTOMock));
    }

    @Test
    @DisplayName("Deveria listar cupons por empresa com sucesso")
    public void deveriaListarCuponsPorEmpresaComSucesso() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cupom> listaMock = new PageImpl<>(cuponsMock, pageable, cuponsMock.size());

        when(cupomRepository.findAllByIdEmpresa(cupomMock.getIdEmpresa(), pageable)).thenReturn(listaMock);
        when(objectMapper.convertValue(cupomMock, CupomDTO.class)).thenReturn(cupomDTOMock);

        Page<CupomDTO> listaDTORetornada = cupomService.findAllByIdEmpresa(cupomMock.getIdEmpresa(), pageable);

        assertNotNull(listaDTORetornada);
        assertEquals(cuponssDTOMock.size(), listaDTORetornada.getContent().size());
        assertIterableEquals(cuponssDTOMock, listaDTORetornada);
    }
}