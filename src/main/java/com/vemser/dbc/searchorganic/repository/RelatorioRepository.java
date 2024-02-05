package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RelatorioRepository extends JpaRepository<Produto, Integer> {
    @Query(value = "SELECT * FROM PRODUTO", nativeQuery = true)
    Page<Produto> findAllProdutosByPreco(Pageable pageable);
}