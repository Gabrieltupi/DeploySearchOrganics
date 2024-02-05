package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.repository.RelatorioRepository;
import com.vemser.dbc.searchorganic.service.interfaces.IRelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService implements IRelatorioService {
    private final RelatorioRepository relatorioRepository;
    private final ObjectMapper objectMapper;

    public Page<RelatorioProdutoPrecoDTO> findAllProdutosByPreco(Pageable pageable) throws Exception {
        Page<Object[]> resultado = relatorioRepository.findAllProdutosByPreco(pageable);

        List<RelatorioProdutoPrecoDTO> relatorioDTOs = new ArrayList<>();

        for (Object[] relatorios : resultado.getContent()) {
            String nome = (String) relatorios[0];
            BigDecimal preco = (BigDecimal) relatorios[1];

            RelatorioProdutoPrecoDTO relatorioDTO = new RelatorioProdutoPrecoDTO(nome, preco);
            relatorioDTOs.add(relatorioDTO);
        }

        return new PageImpl<>(relatorioDTOs, pageable, resultado.getTotalElements());
    }

    public Page<RelatorioProdutoQuantidadeDTO> findAllProdutosByQuantidade(Pageable pageable) throws Exception {
        Page<Object[]> resultado = relatorioRepository.findAllProdutosByQuantidade(pageable);

        List<RelatorioProdutoQuantidadeDTO> relatorioDTOs = new ArrayList<>();

        for (Object[] relatorios : resultado.getContent()) {
            String nome = (String) relatorios[0];
            BigDecimal quantidade = (BigDecimal) relatorios[1];

            RelatorioProdutoQuantidadeDTO relatorioDTO = new RelatorioProdutoQuantidadeDTO(nome, quantidade);
            relatorioDTOs.add(relatorioDTO);
        }

        return new PageImpl<>(relatorioDTOs, pageable, resultado.getTotalElements());
    }
}
