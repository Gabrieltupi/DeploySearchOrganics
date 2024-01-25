package com.vemser.dbc.searchorganic.dto.pedido.validacoes;

import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.exceptions.ValidacaoException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidarProdutosMesmaEmpresa implements  IValidarPedido{
    private final ProdutoRepository produtoRepository;
    @Override
    public void validar(PedidoCreateDTO pedidoCreateDTO, Integer idUsuario) throws Exception {
        Integer idEmpresa = pedidoCreateDTO.getProdutos().get(0).getIdEmpresa();
        for(ProdutoCarrinho produtoCarrinho : pedidoCreateDTO.getProdutos()){
            Produto produto = produtoRepository.buscarProdutoPorId(produtoCarrinho.getIdProduto());
            if(!(produto.getIdEmpresa() == idEmpresa.longValue())){
                throw new ValidacaoException("Produtos não pertencem a mesma empresa");
            }

        }
    }
}
