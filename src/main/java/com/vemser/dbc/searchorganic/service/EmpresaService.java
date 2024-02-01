package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ObjectMapper objectMapper;

    public List<EmpresaDTO> findAll() throws Exception {
        List<Empresa> pessoasEntity =  empresaRepository.findAll();

        return objectMapper.convertValue(pessoasEntity, objectMapper.getTypeFactory().constructCollectionType(List.class, EmpresaDTO.class));
    }

    public EmpresaDTO findById(Integer idEmpresa) throws Exception {
        return retornarDTO(empresaRepository.findById(idEmpresa).orElseThrow(() -> new RegraDeNegocioException("Não encontrado")));
    }

    public EmpresaDTO save(Integer idUsuario, CreateEmpresaDTO empresaDto) {
        Empresa empresa = objectMapper.convertValue(empresaDto, Empresa.class);
        empresa.setIdUsuario(idUsuario);

        return retornarDTO(empresaRepository.save(empresa));
    }

    public EmpresaDTO update(Integer idEmpresa, UpdateEmpresaDTO empresaDto) throws Exception {
        Empresa empresa = objectMapper.convertValue(empresaDto, Empresa.class);
        empresa.setIdEmpresa(idEmpresa);

        return retornarDTO(empresaRepository.save(empresa));
    }

    public void delete(Integer idEmpresa) {
        empresaRepository.deleteById(idEmpresa);
    }

//    public EmpresaDTO preencherInformacoes(Empresa empresa) throws Exception {
//        EmpresaDTO empresaDTO = new EmpresaDTO();
//        empresaDTO.setIdEmpresa(empresa.getIdEmpresa());
//        empresaDTO.setIdUsuario(empresa.getIdUsuario());
//        empresaDTO.setProdutos(empresa.getProdutos());
//        empresaDTO.setNomeFantasia(empresa.getNomeFantasia());
//        empresaDTO.setInscricaoEstadual(empresa.getInscricaoEstadual());
//        empresaDTO.setCnpj(empresa.getCnpj());
//        empresaDTO.setSetor(empresa.getSetor());
//        empresaDTO.setRazaoSocial(empresa.getRazaoSocial());
//        return empresaDTO;
//    }
//
//    public List<Produto> listarProdutosDaLoja(Integer idEmpresa) throws BancoDeDadosException, RegraDeNegocioException {
//        Empresa empresa = empresaRepository.buscaPorId(idEmpresa);
//
//        if (empresa != null) {
//            return produtoRepository.listarProdutosLoja(idEmpresa);
//        }
//        return List.of();
//    }

    private EmpresaDTO retornarDTO(Empresa entity) {
        return objectMapper.convertValue(entity, EmpresaDTO.class);
    }
}

