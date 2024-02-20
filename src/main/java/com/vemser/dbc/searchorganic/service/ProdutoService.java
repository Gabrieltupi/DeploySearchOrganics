package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.pedido.ProdutoPedidoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.service.interfaces.IProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoService implements IProdutoService {
    private final ProdutoRepository produtoRepository;
    private final EmpresaService empresaService;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    public Page<ProdutoDTO> findAll(Pageable pageable) throws Exception {
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        return produtos.map(this::retornarDto);
    }

    public ProdutoDTO findById(Integer id) throws Exception {
        return retornarDto(getById(id));
    }

    public ProdutoDTO save(Integer idEmpresa, ProdutoCreateDTO produtoDto) throws Exception {
        empresaService.findById(idEmpresa);

        Produto produto = convertDto(produtoDto);
        produto.setIdEmpresa(idEmpresa);

        return retornarDto(produtoRepository.save(produto));
    }


    public ProdutoDTO update(Integer idProduto, ProdutoUpdateDTO produtoDto) throws Exception {
        Produto produto = getById(idProduto);
        Integer idUsuarioDonoProd = empresaService.getById(produto.getIdEmpresa()).getIdUsuario();
        if(idUsuarioDonoProd.equals(usuarioService.getIdLoggedUser())||isAdmin()) {
            produto.setUnidadeMedida(produtoDto.getUnidadeMedida());
            produto.setPreco(produtoDto.getPreco());
            produto.setTaxa(produto.getTaxa());
            produto.setCategoria(produtoDto.getCategoria());
            produto.setQuantidade(produtoDto.getQuantidade());
            produto.setUrlImagem(produtoDto.getUrlImagem());
            produto.setNome(produtoDto.getNome());
            produto.setTipoAtivo(produtoDto.getTipoAtivo());
            produto.setDescricao(produtoDto.getDescricao());

            return retornarDto(produtoRepository.save(produto));
        }
        throw new RegraDeNegocioException("Só poderá atualizar seu próprio produto");
    }
    public void delete(Integer idProduto) throws Exception {
        Produto produto = getById(idProduto);
        Integer idUsuarioDonoProd = empresaService.getById(produto.getIdEmpresa()).getIdUsuario();

        if(idUsuarioDonoProd.equals(usuarioService.getIdLoggedUser())||isAdmin()) {
            produtoRepository.delete(produto);
            return;
        }
        throw new RegraDeNegocioException("Só poderá deletar seu próprio produto");
    }

    public boolean isAdmin() {
        Integer userId = usuarioService.getIdLoggedUser();
        Integer count = produtoRepository.existsAdminCargoByUserId(userId);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
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
        return produtoRepository.findById(idProduto).orElseThrow( () -> new RegraDeNegocioException("Produto não encontrado"));
    }

    public Produto convertDto(ProdutoCreateDTO dto) {
        return objectMapper.convertValue(dto, Produto.class);
    }

    public ProdutoDTO retornarDto(Produto entity) {
        return objectMapper.convertValue(entity, ProdutoDTO.class);
    }
}



