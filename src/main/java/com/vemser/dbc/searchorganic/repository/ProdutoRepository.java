package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> porCategoria(TipoCategoria categoria);
    List<Produto>porEmpresa(Integer id);


}
