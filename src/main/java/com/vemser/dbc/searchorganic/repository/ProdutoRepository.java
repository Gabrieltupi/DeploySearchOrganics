package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Produto;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query(value = "SELECT * FROM PRODUTO p WHERE p.id_empresa = :idEmpresa", nativeQuery = true)
    Page<Produto> findAllByIdEmpresa(@Param("idEmpresa") Integer idEmpresa, Pageable pageable);

    @Query(value = "SELECT * FROM PRODUTO p WHERE p.tipo_categoria = :idCategoria", nativeQuery = true)
    Page<Produto> findAllByIdCategoria(@Param("idCategoria") Integer idCategoria, Pageable pageable);
}
