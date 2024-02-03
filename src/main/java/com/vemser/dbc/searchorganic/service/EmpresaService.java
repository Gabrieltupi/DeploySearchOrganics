package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import com.vemser.dbc.searchorganic.service.interfaces.IEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService implements IEmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ObjectMapper objectMapper;

    public List<EmpresaDTO> findAll() throws Exception {
        List<Empresa> empresas =  empresaRepository.findAll();
        return objectMapper.convertValue(empresas, objectMapper.getTypeFactory().constructCollectionType(List.class, EmpresaDTO.class));
    }

    public EmpresaDTO findById(Integer idEmpresa) throws Exception {
        return retornarDto(empresaRepository.findById(idEmpresa).orElseThrow(() -> new RegraDeNegocioException("Empresa não encontrada")));
    }

    public EmpresaDTO save(Integer idUsuario, CreateEmpresaDTO empresaDto) throws Exception {
        Empresa empresa = objectMapper.convertValue(empresaDto, Empresa.class);
        empresa.setIdUsuario(idUsuario);

        return retornarDto(empresaRepository.save(empresa));
    }

    public EmpresaDTO update(Integer idEmpresa, UpdateEmpresaDTO empresaDto) throws Exception {
        findById(idEmpresa);

        Empresa empresa = objectMapper.convertValue(empresaDto, Empresa.class);
        empresa.setIdEmpresa(idEmpresa);

        return retornarDto(empresaRepository.save(empresa));
    }

    public void delete(Integer idEmpresa) throws Exception {
        findById(idEmpresa);
        empresaRepository.deleteById(idEmpresa);
    }

    public List<EmpresaProdutosDTO> findAllWithProdutos() throws Exception {
        List<Empresa> empresas = empresaRepository.findAllWithProdutos();
        return objectMapper.convertValue(empresas, objectMapper.getTypeFactory().constructCollectionType(List.class, EmpresaProdutosDTO.class));
    }

    public EmpresaProdutosDTO findByIdWithProdutos(Integer idEmpresa) throws Exception {
        findById(idEmpresa);
        Optional<Empresa> empresa = empresaRepository.findByIdWithProdutos(idEmpresa);
        return objectMapper.convertValue(empresa, EmpresaProdutosDTO.class);
    }

    private EmpresaDTO retornarDto(Empresa entity) {
        return objectMapper.convertValue(entity, EmpresaDTO.class);
    }
}

