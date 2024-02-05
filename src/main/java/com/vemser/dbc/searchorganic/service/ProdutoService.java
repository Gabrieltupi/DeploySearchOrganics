package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.pedido.ProdutoPedidoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.service.interfaces.IProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoService implements IProdutoService {
    private final ProdutoRepository produtoRepository;
    private final EmpresaService empresaService;
    private final ObjectMapper objectMapper;

    public Page<ProdutoDTO> findAll(Pageable pageable) throws Exception {
        Page<Produto> produtos =  produtoRepository.findAll(pageable);
        return produtos.map(this::retornarDto);
    }

    public ProdutoDTO findById(Integer id) throws Exception {
        return retornarDto(produtoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado")));
    }

    public ProdutoDTO save(Integer idEmpresa, ProdutoCreateDTO produtoDto) throws Exception {
        empresaService.findById(idEmpresa);

        Produto produto = convertDto(produtoDto);
        produto.setIdEmpresa(idEmpresa);

        return retornarDto(produtoRepository.save(produto));
    }

    public ProdutoDTO update(Integer idProduto, ProdutoUpdateDTO produtoDto) throws Exception {
        findById(idProduto);

        Produto produto = objectMapper.convertValue(produtoDto, Produto.class);
        produto.setIdProduto(idProduto);

        return retornarDto(produtoRepository.save(produto));
    }

    public void delete(Integer idProduto) throws Exception {
        findById(idProduto);
        produtoRepository.deleteById(idProduto);
    }

    public Page<ProdutoDTO> findAllByIdEmpresa(Integer idEmpresa, Pageable pageable) throws Exception {
        empresaService.findById(idEmpresa);

        Page<Produto> produtos = produtoRepository.findAllByIdEmpresa(idEmpresa, pageable);
        return produtos.map(this::retornarDto);
    }

    public Page<ProdutoDTO> findAllByIdCategoria(Integer idCategoria, Pageable pageable) throws Exception {
        Page<Produto> produtos = produtoRepository.findAllByIdCategoria(idCategoria, pageable);
        return produtos.map(this::retornarDto);
    }

    public String getMensagemProdutoEmail(List<ProdutoPedidoDTO> produtos) {
        StringBuilder mensagemFinal = new StringBuilder();
        for (ProdutoPedidoDTO produtoPedidoDTO : produtos) {
            String mensagemProduto = String.format("""
                            Nome: %s, Quantidade:  %s, Valor por cada quantidade: R$ %s  <br>
                            """, produtoPedidoDTO.getProduto().getNome(),
                    produtoPedidoDTO.getQuantidade(),
                    produtoPedidoDTO.getProduto().getPreco());
            mensagemFinal.append(mensagemProduto);
        }
        return mensagemFinal.toString();
    }

    public Produto getById(Integer idProduto) throws Exception {
        ProdutoDTO produto = findById(idProduto);
        return convertDto(produto);
    }

    public Produto convertDto(ProdutoCreateDTO dto) {
        return objectMapper.convertValue(dto, Produto.class);
    }

    public ProdutoDTO retornarDto(Produto entity) {
        return objectMapper.convertValue(entity, ProdutoDTO.class);
    }
}



