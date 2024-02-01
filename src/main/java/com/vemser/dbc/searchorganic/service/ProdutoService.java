package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    private final String NOT_FOUND_MESSAGE = "ID não encontrado";


    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produto) throws Exception {
        Produto produtoEntity = convertDto(produto);
        return retornarDto(produtoRepository.save(produtoEntity));

    }

    public ProdutoDTO atualizarProduto(Integer id, ProdutoUpdateDTO produtoDto) throws RegraDeNegocioException{
        Produto produtoRec = findById(id);

            produtoRec.setNome(produtoDto.getNome());
            produtoRec.setDescricao(produtoDto.getDescricao());
            produtoRec.setPreco(produtoDto.getPreco());
            produtoRec.setQuantidade(produtoDto.getQuantidade());
            produtoRec.setCategoria(produtoDto.getCategoria());
            produtoRec.setTaxa(produtoDto.getTaxa());
            produtoRec.setUnidadeMedida(produtoDto.getUnidadeMedida());
            produtoRec.setUrlImagem(produtoDto.getUrlImagem());

        return retornarDto(produtoRepository.save(produtoRec));
    }

    public ProdutoDTO getById(Integer id) throws RegraDeNegocioException {
        Produto entity = findById(id);
        ProdutoDTO dto = retornarDto(entity);
        return dto;
    }

    public Produto findById(Integer id) throws RegraDeNegocioException {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(NOT_FOUND_MESSAGE));
    }

    public Produto convertDto(ProdutoCreateDTO dto) {
        return objectMapper.convertValue(dto, Produto.class);
    }

    public ProdutoDTO retornarDto(Produto entity) {
        return objectMapper.convertValue(entity, ProdutoDTO.class);
    }


    public List<ProdutoDTO> list() {
        return produtoRepository.findAll().stream()
                .map(this::retornarDto)
                .collect(Collectors.toList());
    }

    public void deletarProduto(Integer idProduto) throws Exception {
            Produto entity= findById(idProduto);
            produtoRepository.delete(entity);
    }


    public List<ProdutoDTO> listarProdutosPorCategoria(Integer categoria) {
        TipoCategoria  cate= TipoCategoria.fromInt(categoria);
        List<Produto> produto= produtoRepository.porCategoria(cate);

        List<ProdutoDTO> produtoDTOs = produto.stream()
                .map(this::retornarDto)
                .collect(Collectors.toList());
        return produtoDTOs;
    }

    public List<ProdutoDTO> listarProdutosLoja(Integer idLoja) throws Exception {
        List<Produto> produtos = produtoRepository.porEmpresa(idLoja); // Implemente conforme sua necessidade
        return produtos.stream()
                .map(this::retornarDto)
                .collect(Collectors.toList());
    }

    public String getMensagemProdutoEmail(List<PedidoXProduto> produtos) {
        StringBuilder mensagemFinal = new StringBuilder();
        for (PedidoXProduto pedidoXProduto : produtos) {
            String mensagemProduto = String.format("""
                            Nome: %s, Quantidade:  %s, Valor por cada quantidade: R$ %s  <br>
                            """, pedidoXProduto.getProduto().getNome(),
                    pedidoXProduto.getQuantidade(),
                    pedidoXProduto.getProduto().getPreco());
            mensagemFinal.append(mensagemProduto);
        }
        return mensagemFinal.toString();
    }



}



