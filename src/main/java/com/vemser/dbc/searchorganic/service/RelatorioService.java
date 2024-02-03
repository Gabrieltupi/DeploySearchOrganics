package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoDTO;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.repository.RelatorioRepository;
import com.vemser.dbc.searchorganic.service.interfaces.IRelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelatorioService implements IRelatorioService {
    private final RelatorioRepository relatorioRepository;
    private final ObjectMapper objectMapper;

    public Page<RelatorioProdutoDTO> findAllProdutosByPreco(Pageable pageable) throws Exception {
        Page<Produto> relatorios = relatorioRepository.findAllProdutosByPreco(pageable);
        return relatorios.map(relatorio -> objectMapper.convertValue(relatorio, RelatorioProdutoDTO.class));
    }
}
